package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;
import java.time.*;

import Server.Engine.Helper.DateIntervalCalculator;
import Server.Engine.Interfaces.AuthenticatedEngine;
import Server.Engine.Helper.DateIntervalCalculator;
import Comunication.Reply.SetClosedDaysReply;
import Comunication.Reply.Interfaces.AuthenticatedUpdateReply;

public class SetClosedDaysEngine extends AuthenticatedEngine
{
    private Integer closure_end_date;
    private Integer closure_start_date;
    private PreparedStatement statement;  // DICHIARATO fuori dal try

    public SetClosedDaysEngine
    (
        String data
    ) {
        super(data);
        this.closure_end_date = json.getInt("closure_end_date"); 
        this.closure_start_date = json.getInt("closure_start_date"); 
    }
    
    public AuthenticatedUpdateReply handleRequest()
    {
        if (!connectDB()) 
        {
            return new SetClosedDaysReply(false, false);
        } 
        try 
        {
            if(!petitionerCanLogIn())
            {
                return new SetClosedDaysReply(false, false);
            }
            
            ArrayList<Long> interval = DateIntervalCalculator
                .calculateDateInterval( 1, 16, 2, 15);
                
            if(closure_start_date < interval.get(0) 
                || closure_end_date > interval.get(1))
            {
                return new SetClosedDaysReply(true, false);
            }
            
            String [] roleAndOrg = getRoleAndOrganization();
            String role = roleAndOrg[0];
            String organization = roleAndOrg[1];

            if(!"CONFIGURATOR".equals(role))
            {
                return new SetClosedDaysReply(false, false);
            }
            
            String query = "INSERT INTO closedDays VALUES ( ?, ?, ?)";
            this.statement = connection.prepareStatement(query); 
            
            this.statement.setString(1, organization); 
            this.statement.setInt(2, closure_start_date); 
            this.statement.setInt(3, closure_end_date); 

            if(this.statement.executeUpdate() != 1) 
            {
                return new SetClosedDaysReply(true, true);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new SetClosedDaysReply(false, false); 
        }
        finally  // AGGIUNTO finally block
        {
            if(this.statement != null)
            {
                try {
                    this.statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            disconnectDB();
        }
        return new SetClosedDaysReply(true, false);    
    }
}