public class SetDisponibilityRequest extends AuthenticatedRequest
{
    private List<List<Integer>> disponibilities;

    public SetDisponibilityRequest(
        String userID,
        String password,
        List<List<Integer>> disponibilities
    ) {
        super(ComunicationType.SET_DISPONIBILITY, userID, password);
        this.disponibilities = deepCopy(disponibilities);
    }
    
    private List<List<Integer>> deepCopy(List<List<Integer>> original) {
        if (original == null) {
            return new ArrayList<>();
        }
        
        List<List<Integer>> copy = new ArrayList<>();
        for (List<Integer> innerList : original) {
            copy.add(new ArrayList<>(innerList));
        }
        return copy;
    }

    public String toJSONString()
    {
        JSONArray disponibilitiesArray = new JSONArray();
        
        for (List<Integer> dayList : disponibilities) {
            JSONArray dayArray = new JSONArray();
            for (Integer hour : dayList) {
                dayArray.put(hour);
            }
            disponibilitiesArray.put(dayArray);
        }
        
        json.put("disponibilities", disponibilitiesArray);
        return json.toString();
    }
}