package Server.Engine;

import static org.junit.jupiter.api.Assertions.*;

import Comunication.Request.DeleteEventInstanceRequest;
import Comunication.Request.DeleteEventRequest;
import Comunication.Request.SetNewEventRequest;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import org.json.JSONObject;

import java.io.File;
import java.sql.*;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventEnginesTest {

    private static final String DB_URL = "jdbc:h2:~/documents/ProgettoApollo/databases/MAIN_DB";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWD = "";
    private static final String TERRAFORM_SQL_PATH = "databases/terraform.sql";

    private Connection connection;

    @BeforeEach
    void setUp() throws Exception {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
        resetDatabase(connection);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    private void resetDatabase(Connection conn) throws Exception {
        File scriptFile = new File(TERRAFORM_SQL_PATH);
        if (!scriptFile.exists()) {
            throw new IllegalStateException("Impossibile trovare il file SQL a: " + scriptFile.getAbsolutePath());
        }

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP ALL OBJECTS");
            String absolutePath = scriptFile.getAbsolutePath().replace("\\", "/");
            stmt.execute("RUNSCRIPT FROM '" + absolutePath + "'");
        }
    }

    @Test
    void testSetNewEventEngineSuccessAndDuplicate() throws Exception {
        SetNewEventRequest request = new SetNewEventRequest(
            "Lancillotto.Benacense.99",
            "Altachiara",
            "Concerto al Parco",
            "Concerto di musica classica",
            "Concerto",
            "San Genesio",
            "Desenzano",
            "Parco Le Ninfe",
            "Ingresso est"
        );
        request.addInstance(1775000000, 1775007200, "CONFIRMED", List.of("Arlecchino.Valcalepio.89"), List.of());

        SetNewEventEngine engine = new SetNewEventEngine(request.toJSONString());
        engine.setConnection(connection);

        AuthenticatedReply reply = engine.processWithConnection();
        assertNotNull(reply);
        JSONObject jsonReply = new JSONObject(reply.toJSONString());
        assertTrue(jsonReply.optBoolean("loginSuccessful"));
        assertTrue(jsonReply.optBoolean("updateSuccessful"));

        // Verifica inserimento nel db
        try (PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM events WHERE name = ?")) {
            stmt.setString(1, "Concerto al Parco");
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(1, rs.getInt(1));
        }

        try (PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM eventsData WHERE name = ?")) {
            stmt.setString(1, "Concerto al Parco");
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(1, rs.getInt(1));
        }

        // Duplicato deve fallire
        SetNewEventEngine duplicateEngine = new SetNewEventEngine(request.toJSONString());
        duplicateEngine.setConnection(connection);
        AuthenticatedReply duplicateReply = duplicateEngine.processWithConnection();
        JSONObject duplicateJson = new JSONObject(duplicateReply.toJSONString());
        assertFalse(duplicateJson.optBoolean("updateSuccessful"));
    }

    @Test
    void testDeleteEventInstanceEngineSuccess() throws Exception {
        // 'Cinema in castello' ha istanza con start_date = 1772830800
        DeleteEventInstanceRequest request = new DeleteEventInstanceRequest(
            "Lancillotto.Benacense.99",
            "Altachiara",
            "Cinema in castello",
            1772830800
        );

        DeleteEventInstanceEngine engine = new DeleteEventInstanceEngine(request.toJSONString());
        engine.setConnection(connection);

        AuthenticatedReply reply = engine.processWithConnection();
        assertNotNull(reply);
        JSONObject jsonReply = new JSONObject(reply.toJSONString());
        assertTrue(jsonReply.optBoolean("loginSuccessful"));
        assertTrue(jsonReply.optBoolean("updateSuccessful"));

        // Verifica rimozione istanza da eventsData
        try (PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM eventsData WHERE name = ? AND start_date = ?")) {
            stmt.setString(1, "Cinema in castello");
            stmt.setInt(2, 1772830800);
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(0, rs.getInt(1));
        }
    }

    @Test
    void testDeleteEventEngineSuccess() throws Exception {
        // 'Teatro in rocca' esiste ed ha diverse istanze
        DeleteEventRequest request = new DeleteEventRequest(
            "Lancillotto.Benacense.99",
            "Altachiara",
            "Teatro in rocca"
        );

        DeleteEventEngine engine = new DeleteEventEngine(request.toJSONString());
        engine.setConnection(connection);

        AuthenticatedReply reply = engine.processWithConnection();
        assertNotNull(reply);
        JSONObject jsonReply = new JSONObject(reply.toJSONString());
        assertTrue(jsonReply.optBoolean("loginSuccessful"));
        assertTrue(jsonReply.optBoolean("updateSuccessful"));

        // Verifica eliminazione da events e eventsData
        try (PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM events WHERE name = ?")) {
            stmt.setString(1, "Teatro in rocca");
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(0, rs.getInt(1));
        }

        try (PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM eventsData WHERE name = ?")) {
            stmt.setString(1, "Teatro in rocca");
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(0, rs.getInt(1));
        }
    }

    @Test
    void testEnginesUnauthorizedRole() throws Exception {
        // Arlecchino.Valcalepio.89 è un VOLUNTARY, non CONFIGURATOR
        SetNewEventRequest setReq = new SetNewEventRequest(
            "Arlecchino.Valcalepio.89",
            "Altachiara",
            "Evento Non Autorizzato",
            "Desc",
            "Tipo",
            "San Genesio",
            "Citta",
            "Indirizzo",
            "Ritrovo"
        );
        SetNewEventEngine setEngine = new SetNewEventEngine(setReq.toJSONString());
        setEngine.setConnection(connection);
        AuthenticatedReply setReply = setEngine.processWithConnection();
        assertFalse(new JSONObject(setReply.toJSONString()).optBoolean("updateSuccessful"));

        DeleteEventRequest delReq = new DeleteEventRequest(
            "Arlecchino.Valcalepio.89",
            "Altachiara",
            "Cinema in castello"
        );
        DeleteEventEngine delEngine = new DeleteEventEngine(delReq.toJSONString());
        delEngine.setConnection(connection);
        AuthenticatedReply delReply = delEngine.processWithConnection();
        assertFalse(new JSONObject(delReply.toJSONString()).optBoolean("updateSuccessful"));
    }
}
