package User;

import org.json.JSONArray;

import java.util.HashSet;
import java.util.*;

public class VoluntaryModel extends UserModel
{
    private Set <String> allowedVisits = new HashSet<String>();

    public  VoluntaryModel
            (String username, String cityOfResidence, int yearOfBirth, UserType roleTitle, String organization, boolean pswdNeedsToBeChanged, JSONArray allowedVisits)
    {
        this.username = username;
        this.cityOfResidence = cityOfResidence;
        this.yearOfBirth = yearOfBirth;
        this.organization = organization;
        this.pswdNeedsToBeChanged = pswdNeedsToBeChanged;
        this.roleTitle = roleTitle;
        for (int i = 0; i < allowedVisits.length(); i++)
        {
            this.allowedVisits.add(allowedVisits.getString(i));
        }
    }

    public List<String> getAllowedVisits()
    {
        return new ArrayList<String>(allowedVisits);
    }
}
