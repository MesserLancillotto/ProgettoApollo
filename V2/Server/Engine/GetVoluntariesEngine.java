package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;

public class GetVoluntariesEngine extends Engine 
{
    private String userID;
    private String userPassword;
    private Map<String, Object> filters;

    public GetVoluntariesEngine(
        String userID,
        String userPassword,
        Map<String, Object> filters
    ) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.filters = filters;
    }

    public String handleRequest()
    {
        GetVoluntarieReply response = new GetVoluntarieReply(true); // // Inizializza response
        
        try (Connection connection = connectDB(dbUrl, "sa", "")) {
            String roleCheckQuery = "SELECT role, organization FROM users WHERE userID = ? AND userPassword = ?";
            PreparedStatement roleStatement = connection.prepareStatement(roleCheckQuery);
            roleStatement.setString(1, userID);
            roleStatement.setString(2, userPassword); // // userPassword, non password
            ResultSet result = roleStatement.executeQuery();

            if(!result.next() || !result.getString("role").equals("CONFIGURATOR")) {
                return new GetVoluntarieReply(false).toJSONString(); 
            }

            String organization = result.getString("organization"); 
            
            StringBuilder query = new StringBuilder("""
                SELECT u.userName, u.cityOfResidence, u.birthYear, u.userID, u.role, u.organization, u.changePasswordDue 
                FROM users u WHERE u.organization = ? AND u.role = 'VOLUNTARY'
                """);
            
            List<Object> parameters = new ArrayList<>();
            parameters.add(organization);

            if (filters.containsKey("userName")) {
                query.append(" AND u.userName LIKE ?");
                parameters.add("%" + filters.get("userName") + "%");
            }
            
            if (filters.containsKey("cityOfResidence")) {
                query.append(" AND u.cityOfResidence LIKE ?");
                parameters.add("%" + filters.get("cityOfResidence") + "%");
            }
            
            if (filters.containsKey("birthYear")) {
                query.append(" AND u.birthYear = ?");
                parameters.add(filters.get("birthYear"));
            }
            
            if (filters.containsKey("userID")) {
                query.append(" AND u.userID LIKE ?");
                parameters.add("%" + filters.get("userID") + "%");
            }
            
            if (filters.containsKey("changePasswordDue")) {
                query.append(" AND u.changePasswordDue = ?");
                parameters.add(filters.get("changePasswordDue"));
            }
            
            if (filters.containsKey("birthYear")) {
                if((boolean)filters.get("olderThanYear"))
                {
                    query.append(" AND u.birthYear >= ?");
                } else {
                    query.append(" AND u.birthYear <= ?");
                }
                parameters.add(filters.get("birthYear"));
            }

            PreparedStatement statement = connection.prepareStatement(query.toString());
            
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            
            ResultSet usersResult = statement.executeQuery();
            
            while (usersResult.next()) {
                String currentUserID = usersResult.getString("userID");
                
                String visitTypesQuery = "SELECT visitType FROM allowedVisits WHERE userID = ?";
                PreparedStatement visitTypesStmt = connection.prepareStatement(visitTypesQuery);
                visitTypesStmt.setString(1, currentUserID);
                ResultSet visitTypesResult = visitTypesStmt.executeQuery();
                
                ArrayList<String> allowedVisitTypes = new ArrayList<>();
                while (visitTypesResult.next()) {
                    allowedVisitTypes.add(visitTypesResult.getString("visitType"));
                }
                
                response.insertUser(
                    usersResult.getString("userName"),
                    usersResult.getString("cityOfResidence"),
                    usersResult.getInt("birthYear"),
                    usersResult.getString("role"),
                    currentUserID,
                    usersResult.getString("organization"),
                    usersResult.getBoolean("changePasswordDue"),
                    allowedVisitTypes
                );
                visitTypesStmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new GetVoluntarieReply(false).toJSONString();
        }
            
        return response.toJSONString();
    }
}