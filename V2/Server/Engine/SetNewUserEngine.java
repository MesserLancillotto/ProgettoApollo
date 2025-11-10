package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;
import RequestReply.UserRoleTitle.*;
import RequestReply.ComunicationType.*;

public class SetNewUserEngine extends Engine
{
        private String userID;
        private String userPassword;
        private String userName;
        private String newPassword;
        private String cityOfResidence;
        private int birthYear;
        private UserRoleTitle role;

        public SetNewUserEngine(
                String userID,
                String userPassword,
                String userName,
                String newPassword,
                String cityOfResidence,
                int birthYear,
                UserRoleTitle role
        ) {
                this.userID = userID;
                this.userPassword = userPassword;
                this.userName = userName;
                this.newPassword = newPassword;
                this.cityOfResidence = cityOfResidence;
                this.birthYear = birthYear;
                this.role = role;
        }

        public String handleRequest()
        {
                try
                (
                        Connection connection = connectDB(dbUrl, "sa", "");
                ) {
                        String query = "SELECT role, organization FROM users WHERE userID = ? AND userPassword = ?";
                        PreparedStatement selectStatement = connection.prepareStatement(query); 
                        selectStatement.setString(1, userID);
                        selectStatement.setString(2, userPassword);

                        ResultSet resultSet = selectStatement.executeQuery();
                        String currentRole = ""; 
                        if (resultSet.next()) {
                            currentRole = resultSet.getString("role");
                        } else {
                            throw new Exception("Exception: temporary user not found");
                        }
                        if
                        (
                            UserRoleTitleStringConverter.stringToRole(currentRole) 
                            != UserRoleTitle.TEMPORARY
                        ) {
                            throw new Exception("Exception: user not temporary");
                        } 
                        
                        String organization = resultSet.getString("organization");
                        String deleteTmpUser = "DELETE FROM users WHERE userID = ?";
                        PreparedStatement deleteStatement = connection.prepareStatement(deleteTmpUser); 
                        deleteStatement.setString(1, userID);
                        deleteStatement.executeUpdate(); 
                        
                        String newUserID = String.format("%s.%s.%d",
                                UserRoleTitleStringConverter.roleToString(this.role),
                                userName.replaceAll(" ", "."),
                                birthYear % 100);
                        String insertQuery = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?)"; 
                        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                        insertStatement.setString(1, userName);
                        insertStatement.setString(2, cityOfResidence);  
                        insertStatement.setInt(3, birthYear); 
                        insertStatement.setString(4, newUserID); 
                        insertStatement.setString(5, newPassword);
                        insertStatement.setString(6, this.role.name());
                        insertStatement.setString(7, organization);
                        
                        if(insertStatement.executeUpdate() == 1)
                        {
                                return new SetNewUserReply(true, newUserID).toJSONString(); 
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new SetNewUserReply(false, "").toJSONString(); //
        }
    }