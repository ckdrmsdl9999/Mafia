package mafiaclient;

/**
 * MafiaClient.java
 * Contains the MafiaClient class
 * @author Cory Gehr (cmg5573)
 */
public class MafiaClient {
    
    public static String HOST_NAME;
    public static int PORT_NUMBER = 65004;
    
    /**
     * main()
     * Entry point for the MafiaClient application
     * @param args Command Line arguments
     */
    public static void main(String[] args) {
        
        if(args.length > 0) {
            // Host name set
            HOST_NAME = args[0];
        } else {
            // Host name not set, run on localhost
            HOST_NAME = "localhost";
        }
        
        // Start client connection
        ClientServerConnectionStarter connection = 
                new ClientServerConnectionStarter(HOST_NAME, PORT_NUMBER);
        connection.start();
        
    }
}
