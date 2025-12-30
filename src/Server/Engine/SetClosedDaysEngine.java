package Server.Engine;

import org.json.*;
import java.sql.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Server.Engine.Helper.DateIntervalCalculator;
import Comunication.Reply.AuthenticatedUpdateReply;
import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.SetClosedDaysReply;

public class SetClosedDaysEngine extends AuthenticatedEngine
{
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
            
            List<long[]> interval = DateIntervalCalculator
                .calculateDayIntervalsForFutureMonths(
                    Instant.now().getEpochSecond(),
                    3,
                    16, 
                    16
                );
            long[] monthInterval = interval[2];
            if(closure_start_date < monthInterval[0] || closure_end_date > monthInterval[1])
            {
                return SetClosedDaysReply(true, false);
            }
            
            String [] roleAndOrg = getRoleAndOrganization();
            String role = roleAndOrg[0];
            String organization = roleAndOrg[1];

            if(!"CONFIGURATOR".equals(role))
            {
                return new SetClosedDaysReply(false, false);
            }
            
            String query = "INSERT INTO closedDays VALUES ( ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, organization);
            statement.setInt(2, closure_start_date);
            statement.setInt(3, closure_end_date);

            if(statement.executeUpdate() != 1)
            {
                return new SetClosedDaysReply(true, true);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new SetClosedDaysReply(true, false);    
    }
}
