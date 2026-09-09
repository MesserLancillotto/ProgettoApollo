package User;

import java.util.Collections;
import java.util.List;

public class EventDTO {
    private final String name;
    private final String description;
    private final String visitType;
    private final String city;
    private final String address;
    private final String randezvous;
    private final List<EventInstanceDTO> instances;

    public EventDTO(String name, String description, String visitType, String city, String address, String randezvous, List<EventInstanceDTO> instances) {
        this.name = name;
        this.description = description;
        this.visitType = visitType;
        this.city = city;
        this.address = address;
        this.randezvous = randezvous;
        this.instances = instances != null ? instances : Collections.emptyList();
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getVisitType() { return visitType; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public String getRandezvous() { return randezvous; }
    public List<EventInstanceDTO> getInstances() { return instances; }

    public String getFullLocation() {
        StringBuilder sb = new StringBuilder();
        if (city != null && !city.isEmpty()) sb.append(city);
        if (address != null && !address.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(address);
        }
        if (randezvous != null && !randezvous.isEmpty()) {
            if (sb.length() > 0) sb.append(" (").append(randezvous).append(")");
            else sb.append(randezvous);
        }
        return sb.toString();
    }
}
