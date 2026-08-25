package Server.Engine;

import static org.junit.jupiter.api.Assertions.*;

import Comunication.Request.GetPersonalDataRequest;
import Comunication.Reply.GetPersonalDataReply;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Comunication.Reply.Interfaces.AuthenticatedReply;

public class GetPersonalDataEngineTest 
{
    private static final String DB_URL 
        = "jdbc:h2:~/documents/ProgettoApollo/databases/MAIN_DB";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWD = "";

    private static final String TERRAFORM_SQL_PATH = "databases/terraform.sql";

    private Connection connection;

    @BeforeEach
    void setUp() throws Exception 
    {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
        resetDatabase(connection);
    }

    @AfterEach
    void tearDown() throws Exception 
    {
        if (connection != null && !connection.isClosed()) 
        {
            connection.close();
        }
    }

    private void resetDatabase(Connection conn) throws Exception 
    {
        File scriptFile = new File(TERRAFORM_SQL_PATH);
        if (!scriptFile.exists()) 
        {
            throw new IllegalStateException("Impossibile trovare il file SQL a: " + scriptFile.getAbsolutePath());
        }

        try (Statement stmt = conn.createStatement()) 
        {
            stmt.execute("DROP ALL OBJECTS");
            String absolutePath = scriptFile.getAbsolutePath().replace("\\", "/");
            stmt.execute("RUNSCRIPT FROM '" + absolutePath + "'");
        }
    }

    @Test
    void testPetitionerCanLogIn() throws Exception 
    {
        GetPersonalDataRequest request = new GetPersonalDataRequest(
            "Lancillotto.Benacense.99", "Altachiara");

        GetPersonalDataEngine engine 
            = new GetPersonalDataEngine(request.toJSONString());
        engine.setConnection(connection);

        assertTrue(engine.petitionerCanLogIn(), 
            "User in terraform.sql should be able to authenticate");

        AuthenticatedReply reply = engine.processWithConnection();
        assertNotNull(reply, "Engine answer should not be null");
    }

    @Test 
    void testProcessWithConnection() throws Exception 
    {
        GetPersonalDataRequest request = new GetPersonalDataRequest(
            "Lancillotto.Benacense.99", "Altachiara");

        GetPersonalDataEngine engine 
            = new GetPersonalDataEngine(request.toJSONString());
        engine.setConnection(connection);

        assertTrue(engine.petitionerCanLogIn(), 
            "Authentication correct");

        AuthenticatedReply reply = engine.processWithConnection();
        assertNotNull(reply, "Engine answer should not be null");
        assertTrue(reply instanceof GetPersonalDataReply, 
            "Engine answer instance of GetPersonalDataReply");

        JSONObject replyJson = new JSONObject(reply.toJSONString());

        assertTrue(replyJson.optBoolean("success", true), 
            "Answer should be a success");

        JSONObject userJson = replyJson.getJSONObject("user");

        assertEquals("Lancillotto.Benacense.99", userJson.getString("userID"));
        assertEquals("Lancillotto", userJson.getString("name"));
        assertEquals("Benacense", userJson.getString("surname"));
        assertEquals("Desenzano", userJson.getString("city"));
        assertEquals(23, userJson.getInt("birth_dd"));
        assertEquals(12, userJson.getInt("birth_mm"));
        assertEquals(1999, userJson.getInt("birth_yy"));
        assertEquals(1767776400, userJson.getInt("user_since"));
        assertEquals("San Genesio", userJson.getString("organization"));
        assertEquals("CONFIGURATOR", userJson.getString("role"));
        assertFalse(userJson.getBoolean("changePasswordDue"));
    }

    @Test 
    void testProcessWithConnectionShouldFail() throws Exception 
    {
        GetPersonalDataRequest request = new GetPersonalDataRequest(
            "Testuser.Shouldfail.404", "InexistentPassword");

        GetPersonalDataEngine engine 
            = new GetPersonalDataEngine(request.toJSONString());
        engine.setConnection(connection);

        assertFalse(engine.petitionerCanLogIn(), 
            "Authentication correct");

        AuthenticatedReply reply = engine.processWithConnection();
        assertNotNull(reply, "Engine answer should not be null");
        assertTrue(reply instanceof GetPersonalDataReply, 
            "Engine answer instance of GetPersonalDataReply");
    }
}
