package User;

import java.util.Collections;
import java.util.List;

public class EventInstanceDTO {
    private final Integer startDate;
    private final Integer endDate;
    private final List<String> voluntaries;
    private final List<String> users;

    public EventInstanceDTO(Integer startDate, Integer endDate, List<String> voluntaries, List<String> users) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.voluntaries = voluntaries != null ? voluntaries : Collections.emptyList();
        this.users = users != null ? users : Collections.emptyList();
    }

    public Integer getStartDate() { return startDate; }
    public Integer getEndDate() { return endDate; }
    public List<String> getVoluntaries() { return voluntaries; }
    public List<String> getUsers() { return users; }

    public boolean hasVoluntary(String username) {
        if (username == null) return false;
        return voluntaries.stream().anyMatch(v -> v.equalsIgnoreCase(username));
    }
}
