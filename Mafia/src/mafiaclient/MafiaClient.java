package mafiaclient;

/**
 * MafiaClient.java
 * Contains the MafiaClient class
 * @author Cory Gehr (cmg5573)
 */
public class MafiaClient {
    
    public static String HOST_NAME = "localhost";
    public static int PORT_NUMBER = 65004;
    
    /**
     * main()
     * Entry point for the MafiaClient application
     * @param args Command Line arguments
     */
    public static void main(String[] args) {
        // Start client connection
        ClientServerConnectionStarter connection = 
                new ClientServerConnectionStarter(HOST_NAME, PORT_NUMBER);
        connection.start();
    }
}
