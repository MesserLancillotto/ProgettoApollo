package User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public final class DataMapper {
    private DataMapper() {}

    /**
     * Verifica se la risposta del server indica un'operazione andata a buon fine.
     */
    public static boolean isOperationSuccessful(String jsonResponse) {
        if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
            return false;
        }
        try {
            JSONObject response = new JSONObject(jsonResponse);
            if (response.optBoolean("loginSuccessful", false)) {
                return response.optBoolean("updateSuccessful", false);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Converte la risposta JSON dei volontari in una lista tipizzata di VoluntaryDTO.
     */
    public static List<VoluntaryDTO> parseVoluntaries(String jsonResponse) {
        List<VoluntaryDTO> list = new ArrayList<>();
        if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
            return list;
        }

        try {
            JSONObject root = new JSONObject(jsonResponse);
            if (root.optBoolean("loginSuccessful", false) && root.has("voluntaries")) {
                JSONArray array = root.getJSONArray("voluntaries");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String userID = obj.optString("userID", "");
                    String name = obj.optString("name", "");
                    String surname = obj.optString("surname", "");
                    String city = obj.optString("city", "");
                    String organization = obj.optString("organization", "");

                    List<String> allowedVisits = new ArrayList<>();
                    if (obj.has("allowedVisits")) {
                        JSONArray visitsArr = obj.getJSONArray("allowedVisits");
                        Set<String> uniqueVisits = new LinkedHashSet<>();
                        for (int j = 0; j < visitsArr.length(); j++) {
                            uniqueVisits.add(visitsArr.getString(j));
                        }
                        allowedVisits.addAll(uniqueVisits);
                    }

                    list.add(new VoluntaryDTO(userID, name, surname, city, organization, allowedVisits));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Converte la risposta JSON dei luoghi in una lista tipizzata di PlaceDTO.
     */
    public static List<PlaceDTO> parsePlaces(String jsonResponse) {
        List<PlaceDTO> list = new ArrayList<>();
        if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
            return list;
        }

        try {
            JSONObject root = new JSONObject(jsonResponse);
            if (root.optBoolean("loginSuccessful", false) && root.has("places")) {
                JSONArray array = root.getJSONArray("places");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String city = obj.optString("city", "");
                    String address = obj.optString("address", "");
                    String visitType = obj.optString("visitType", "");
                    String description = obj.optString("description", "");
                    String organization = obj.optString("organization", "");
                    String defaultVoluntary = obj.optString("defaultVoluntary", "");

                    list.add(new PlaceDTO(city, address, visitType, description, organization, defaultVoluntary));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Converte la risposta JSON degli eventi in una lista tipizzata di EventDTO.
     */
    public static List<EventDTO> parseEvents(String jsonResponse) {
        List<EventDTO> list = new ArrayList<>();
        if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
            return list;
        }

        try {
            JSONArray array;
            if (jsonResponse.trim().startsWith("[")) {
                array = new JSONArray(jsonResponse);
            } else {
                JSONObject root = new JSONObject(jsonResponse);
                if (!root.optBoolean("loginSuccessful", false) && root.has("loginSuccessful")) {
                    return list;
                }
                array = root.optJSONArray("events");
            }

            if (array == null) return list;

            for (int i = 0; i < array.length(); i++) {
                JSONObject eventObj = array.getJSONObject(i);
                String name = eventObj.optString("name", "");
                String description = eventObj.optString("description", "");
                String visitType = eventObj.optString("visitType", "");
                String city = eventObj.optString("city", "");
                String address = eventObj.optString("address", "");
                String randezvous = eventObj.optString("randezvous", "");

                List<EventInstanceDTO> instances = new ArrayList<>();
                if (eventObj.has("instances")) {
                    JSONArray instArr = eventObj.getJSONArray("instances");
                    for (int j = 0; j < instArr.length(); j++) {
                        JSONObject instObj = instArr.getJSONObject(j);
                        Integer startDate = instObj.has("start_date") ? instObj.getInt("start_date") : null;
                        Integer endDate = instObj.has("end_date") ? instObj.getInt("end_date") : null;

                        List<String> voluntaries = new ArrayList<>();
                        if (instObj.has("voluntaries")) {
                            JSONArray volArr = instObj.getJSONArray("voluntaries");
                            for (int k = 0; k < volArr.length(); k++) {
                                voluntaries.add(volArr.getString(k));
                            }
                        }

                        List<String> users = new ArrayList<>();
                        JSONArray usersArr = instObj.has("users") ? instObj.getJSONArray("users") :
                                instObj.has("event_users") ? instObj.getJSONArray("event_users") : null;
                        if (usersArr != null) {
                            for (int m = 0; m < usersArr.length(); m++) {
                                users.add(usersArr.getString(m));
                            }
                        }

                        instances.add(new EventInstanceDTO(startDate, endDate, voluntaries, users));
                    }
                }

                list.add(new EventDTO(name, description, visitType, city, address, randezvous, instances));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Estrae l'insieme univoco di tipi di visita disponibili dagli eventi.
     */
    public static Set<String> extractVisitTypes(List<EventDTO> events) {
        Set<String> types = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (EventDTO event : events) {
            if (event.getVisitType() != null && !event.getVisitType().trim().isEmpty()) {
                types.add(event.getVisitType().trim().toUpperCase());
            }
        }
        return types;
    }
}
