package Server.Engine;

import org.json.*;
import java.sql.*;
import java.util.*;

import Server.Engine.Interfaces.AuthenticatedEngine;
import Comunication.Reply.Interfaces.ReplyInterface;
import Comunication.Reply.Interfaces.AuthenticatedReply;
import Comunication.Reply.GetPersonalDataReply;
import Comunication.DatabaseObjects.User;
import Comunication.DatabaseObjects.UserRole;
import Server.Engine.Helper.*;

public class GetPersonalDataEngine extends AuthenticatedEngine
{
    public GetPersonalDataEngine(String data) 
    {
        super(data);
    }
    
    public AuthenticatedReply processWithConnection() throws SQLException
    {        
        User user = UserCreator.createUserFromID(connection, getUserID());   
        return new GetPersonalDataReply(true, user);
    } 
}