package Server.Engine;

import java.sql.*;
import java.util.*;

public class Engine
{
    protected static String dbUrl = "jdbc:h2:~/documents/IngegneriaDelSoftware2025/databases/MAIN_DB";

    public static final Connection connectDB(String url, String user, String passwd)
    {
        try
        {
            return DriverManager.getConnection(url, user, passwd); //
        } catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}