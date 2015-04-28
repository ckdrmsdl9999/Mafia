package mafiaclient;

/**
 * MafiaClient.java
 * Contains the MafiaClient class
 * @author Cory Gehr (cmg5573)
 */
public class MafiaClient {
    
    /**
     * main()
     * Entry point for the MafiaClient application
     * @param args Command Line arguments
     */
    public static void main(String[] args) {
        // Start client connection
        ClientServerConnectionStarter connection = 
                new ClientServerConnectionStarter(args[0], 
                        Integer.parseInt(args[1]));
        connection.start();
    }
}
