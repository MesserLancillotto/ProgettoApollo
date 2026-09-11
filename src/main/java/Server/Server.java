package Server;

public class Server
{
    public static void main(String [] args)
    {
        while(!Thread.currentThread().isInterrupted())
        {
            Thread api = new ServerAPI();
            api.start(); 
            try 
            {
                api.join();
            } 
            catch (InterruptedException e) 
            {
                Thread.currentThread().interrupt();
            }
        }
    }
}
