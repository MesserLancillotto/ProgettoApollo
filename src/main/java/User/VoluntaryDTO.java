package User;

import java.util.Collections;
import java.util.List;

public class VoluntaryDTO {
    private final String userID;
    private final String name;
    private final String surname;
    private final String city;
    private final String organization;
    private final List<String> allowedVisits;

    public VoluntaryDTO(String userID, String name, String surname, String city, String organization, List<String> allowedVisits) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.organization = organization;
        this.allowedVisits = allowedVisits != null ? allowedVisits : Collections.emptyList();
    }

    public String getUserID() { return userID; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getCity() { return city; }
    public String getOrganization() { return organization; }
    public List<String> getAllowedVisits() { return allowedVisits; }
}
