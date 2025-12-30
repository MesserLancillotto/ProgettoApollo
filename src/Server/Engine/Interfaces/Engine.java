package Server.Engine.Interfaces;

import org.json.*;
import java.sql.*;

import Comunication.Reply.Interfaces.ReplyInterface;

abstract class Engine implements EngineInterface
{
    public JSONObject json;
    
    private static String dbUrl = 
    """
        jdbc:h2:~/documents/IngegneriaDelSoftware2025/databases/MAIN_DB
    """;
    private static String user = "sa";
    private static String db_passwd = "";
}