package User;

public class PlaceDTO {
    private final String city;
    private final String address;
    private final String visitType;
    private final String description;
    private final String organization;
    private final String defaultVoluntary;

    public PlaceDTO(String city, String address, String visitType, String description, String organization, String defaultVoluntary) {
        this.city = city;
        this.address = address;
        this.visitType = visitType;
        this.description = description;
        this.organization = organization;
        this.defaultVoluntary = defaultVoluntary;
    }

    public String getCity() { return city; }
    public String getAddress() { return address; }
    public String getVisitType() { return visitType; }
    public String getDescription() { return description; }
    public String getOrganization() { return organization; }
    public String getDefaultVoluntary() { return defaultVoluntary; }
}
