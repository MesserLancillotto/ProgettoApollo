package User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserFactoryTest 
{
    @Test
    void testCreateConfiguratorFromServerResponse() 
    {
        String serverResponse = """
        {
            "loginSuccessful": true,
            "user": {
                "userID": "Lancillotto.Benacense.99",
                "name": "Lancillotto",
                "surname": "Benacense",
                "city": "Desenzano",
                "birth_dd": 23,
                "birth_mm": 12,
                "birth_yy": 1999,
                "user_since": 1767776400,
                "role": "CONFIGURATOR",
                "changePasswordDue": false,
                "organization": "San Genesio",
                "disponibilities": [],
                "allowedVisits": []
            }
        }
        """;

        JSONObject json = new JSONObject(serverResponse);
        UserModel user = UserFactory.create_user("Lancillotto.Benacense.99", json);

        assertNotNull(user);
        assertInstanceOf(ConfiguratorModel.class, user);
        assertEquals("Lancillotto.Benacense.99", user.getUsername());
        assertEquals("Lancillotto", user.getName());
        assertEquals("Benacense", user.getSurname());
        assertEquals("Desenzano", user.getCityOfResidence());
        assertEquals(1999, user.getYearOfBirth());
        assertEquals("San Genesio", user.getOrganization());
        assertEquals(UserType.CONFIGURATOR, user.getRoleTitle());
        assertFalse(user.getPasswordNeedsToBeChanged());
    }

    @Test
    void testCreateVoluntaryFromServerResponse() 
    {
        String serverResponse = """
        {
            "loginSuccessful": true,
            "user": {
                "userID": "Arlecchino.Valcalepio.89",
                "name": "Arlecchino",
                "surname": "Valcalepio",
                "city": "Desenzano",
                "birth_dd": 15,
                "birth_mm": 10,
                "birth_yy": 1989,
                "role": "VOLUNTARY",
                "changePasswordDue": true,
                "organization": "San Genesio",
                "allowedVisits": ["GUIDATA", "LIBERA"]
            }
        }
        """;

        JSONObject json = new JSONObject(serverResponse);
        UserModel user = UserFactory.create_user("Arlecchino.Valcalepio.89", json);

        assertNotNull(user);
        assertInstanceOf(VoluntaryModel.class, user);
        VoluntaryModel vol = (VoluntaryModel) user;
        assertEquals(UserType.VOLUNTARY, vol.getRoleTitle());
        assertTrue(vol.getPasswordNeedsToBeChanged());
        assertEquals(2, vol.getAllowedVisits().size());
        assertTrue(vol.getAllowedVisits().contains("GUIDATA"));
    }

    @Test
    void testCreateBeneficiaryFromLegacyMockFormat() 
    {
        String mockResponse = """
        {
            "loginSuccessful": true,
            "passwordChangeDue": false,
            "userName": "BENEFICIARY.Mario.Rossi.1995",
            "cityOfResidence": "Milano",
            "birthYear": 1995,
            "role": "BENEFICIARY",
            "organization": "Milano Ovest"
        }
        """;

        JSONObject json = new JSONObject(mockResponse);
        UserModel user = UserFactory.create_user("BENEFICIARY.Mario.Rossi.1995", json);

        assertNotNull(user);
        assertInstanceOf(BeneficiaryModel.class, user);
        assertEquals("Milano", user.getCityOfResidence());
        assertEquals(1995, user.getYearOfBirth());
        assertEquals(UserType.BENEFICIARY, user.getRoleTitle());
    }

    @Test
    void testCreateUserNullOrUnknownRole() 
    {
        assertNull(UserFactory.create_user("randomUser", null));

        JSONObject unknown = new JSONObject();
        unknown.put("role", "SUPERADMIN");
        assertNull(UserFactory.create_user("randomUser", unknown));
    }
}
