package Server;

public class Server
{
    public static void main(String [] args)
    {
        ServerAPI api = new ServerAPI();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            api.stopServer();
        }));

        api.start();

        try {
            api.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}