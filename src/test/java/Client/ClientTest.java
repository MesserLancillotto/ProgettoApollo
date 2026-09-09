package Client;

import org.json.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest 
{
    private Client client;

    @BeforeEach
    void setUp() 
    {
        client = Client.getInstance();
    }

    @Test
    void testSingletonInstance() 
    {
        Client secondInstance = Client.getInstance();
        assertNotNull(client);
        assertSame(client, secondInstance);
    }

    @Test
    void testReadJSONObjectFromFile(@TempDir Path tempDir) throws IOException 
    {
        Path jsonFile = tempDir.resolve("test_object.json");
        Files.writeString(jsonFile, "{\"status\":\"OK\",\"code\":200}");

        JSONObject result = Client.readJSONObjectFromFile(jsonFile.toString());
        assertNotNull(result);
        assertEquals("OK", result.getString("status"));
        assertEquals(200, result.getInt("code"));
    }

    @Test
    void testReadJSONObjectFromFileFileNotFound() 
    {
        JSONObject result = Client.readJSONObjectFromFile("file_inexistente_12345");
        assertNull(result);
    }

    @Test
    void testReadJSONArrayFromFile(@TempDir Path tempDir) throws IOException 
    {
        Path jsonFile = tempDir.resolve("test_array.json");
        Files.writeString(jsonFile, "[\"item1\", \"item2\"]");

        JSONArray result = Client.readJSONArrayFromFile(jsonFile.toString());
        assertNotNull(result);
        assertEquals(2, result.length());
        assertEquals("item1", result.getString(0));
    }

    @Test
    void testSetAndGetMaxPeopleSubscription(@TempDir Path tempDir) throws Exception 
    {
        Path configFile = tempDir.resolve("config_test.json");
        
        // Iniezione del percorso temporaneo tramite reflection per isolare il file fisco
        Field filePathField = Client.class.getDeclaredField("filePath");
        filePathField.setAccessible(true);
        filePathField.set(client, configFile.toString());

        client.set_max_people_subscription(10);
        
        assertTrue(Files.exists(configFile));
        assertEquals(10, client.get_max_people_subscription());
    }

    @Test
    void testGetMaxPeopleSubscriptionFileNotFound(@TempDir Path tempDir) throws Exception 
    {
        Path nonExistentFile = tempDir.resolve("non_existent.json");

        Field filePathField = Client.class.getDeclaredField("filePath");
        filePathField.setAccessible(true);
        filePathField.set(client, nonExistentFile.toString());

        assertEquals(-1, client.get_max_people_subscription());
    }

    @Test
    void testMakeServerRequestStateMutations(@TempDir Path tempDir) throws Exception 
    {
        // Dummy JSON per verificare la lettura
        Path jsonFile = tempDir.resolve("get_event.json");
        Files.writeString(jsonFile, "{\"event\":\"Cinema\"}");

        // Iniezione mock del path file se necessario, qui testiamo il settaggio dello stato interno
        client.get_event("CONFIRMED");

        Field whichFileField = Client.class.getDeclaredField("whichFile");
        whichFileField.setAccessible(true);
        assertEquals("get_event.json", whichFileField.get(client));

        Field isObjectField = Client.class.getDeclaredField("isObject");
        isObjectField.setAccessible(true);
        assertTrue((Boolean) isObjectField.get(client));
    }

    @Test
    void testRequestCreationPersonalData()
    {
        client.setUserID("testUser");
        client.setUserPassword("testPass");
        client.get_personal_data("testUser");

        assertNotNull(client.getCurrentRequest());
        JSONObject json = new JSONObject(client.getCurrentRequest().toJSONString());
        assertEquals("GET_PERSONAL_DATA", json.getString("comunicationType"));
        assertEquals("testUser", json.getString("userID"));
        assertEquals("testPass", json.getString("password"));
    }

    @Test
    void testRequestCreationChangePassword()
    {
        client.setUserID("testUser");
        client.setUserPassword("oldPass");
        client.change_password("newSecretPass");

        assertNotNull(client.getCurrentRequest());
        JSONObject json = new JSONObject(client.getCurrentRequest().toJSONString());
        assertEquals("SET_NEW_PASSWORD", json.getString("comunicationType"));
        assertEquals("newSecretPass", json.getString("newPassword"));
    }

    @Test
    void testRequestCreationSetUserSubscriptionToEvent()
    {
        client.setUserID("user1");
        client.setUserPassword("pass1");
        client.set_user_subscription_to_event(List.of("friend1", "friend2"), "Concerto", 1700000000);

        assertNotNull(client.getCurrentRequest());
        JSONObject json = new JSONObject(client.getCurrentRequest().toJSONString());
        assertEquals("SET_USER_SUBSCRIPTION_TO_EVENT", json.getString("comunicationType"));
        assertEquals("Concerto", json.getString("eventName"));
        assertEquals(1700000000, json.getInt("date"));
        assertEquals(2, json.getJSONArray("friends").length());
    }

    @Test
    void testRequestCreationDeletePlace()
    {
        client.setUserID("admin");
        client.setUserPassword("adminPass");
        client.delete_place("Brescia", "Via Roma 1", "GUIDATA");

        assertNotNull(client.getCurrentRequest());
        JSONObject json = new JSONObject(client.getCurrentRequest().toJSONString());
        assertEquals("DELETE_PLACE", json.getString("comunicationType"));
        assertEquals("Brescia", json.getString("city"));
        assertEquals("Via Roma 1", json.getString("address"));
        assertEquals("GUIDATA", json.getString("visitType"));
    }
}