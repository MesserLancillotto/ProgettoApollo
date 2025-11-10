package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server
{
    public static void main(String [] args) 
    {
            ServerAPI api = new ServerAPI();
            api.start(); 
    }
}
