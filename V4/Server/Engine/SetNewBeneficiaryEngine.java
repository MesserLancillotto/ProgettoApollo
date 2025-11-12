package Server.Engine;

import java.sql.*;
import RequestReply.Reply.*;
import java.util.*;
import RequestReply.UserRoleTitle.*;
import RequestReply.ComunicationType.*;

public class SetNewBeneficiaryEngine extends Engine
{
        private String userID;
        private String userPassword;
        private String userName;
        private String newPassword;
        private String cityOfResidence;
        private int birthYear;
        private UserRoleTitle role;

        public SetNewBeneficiaryEngine(
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
                        insertStatement.setString(6, "USER");
                        insertStatement.setString(7, "");
                        
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