package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;
import java.time.*;

import Server.Engine.Helper.DateIntervalCalculator;
import Server.Engine.Interfaces.AuthenticatedEngine;
import Server.Engine.Helper.DateIntervalCalculator;
import Comunication.Reply.SetClosedDaysReply;
import Comunication.Reply.Interfaces.AuthenticatedReply;

public class SetClosedDaysEngine extends AuthenticatedEngine
{
    private static final String QUERY 
        = "INSERT INTO closedDays VALUES ( ?, ?, ?)";

    private Integer closure_end_date;
    private Integer closure_start_date;

    public SetClosedDaysEngine
    (
        String data
    ) {
        super(data);
        this.closure_end_date = json.getInt("closure_end_date"); 
        this.closure_start_date = json.getInt("closure_start_date"); 
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {
        String role = getRole();
        String organization = getOrganization();

        if(!petitionerIsConfigurator())
        {
            System.out.println("NON CONFIG");
            return new SetClosedDaysReply(true, false);
        }
        

        System.out.println("CONFIG");

        this.statement = connection.prepareStatement(QUERY); 
        
        this.statement.setString(1, organization); 
        this.statement.setInt(2, closure_start_date); 
        this.statement.setInt(3, closure_end_date); 


        System.out.println(statement);

        if(this.statement.executeUpdate() == 1) 
        {
            return new SetClosedDaysReply(true, true);
        }
        return new SetClosedDaysReply(true, false);
    }
}