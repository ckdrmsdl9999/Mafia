package mafiaclient;

import java.io.IOException;
import java.net.Socket;

/**
 * ClientServerConnectionStarter.java
 * Contains the ClientServerConnectionStarter class
 * @author Cory Gehr (cmg5573)
 */
public class ClientServerConnectionStarter extends Thread {
    
    private final String hostname; // Hostname
    private final int port;        // Port Number
    
    /**
     * ClientServerConnectionStarter()
     * Constructor for the ClientServerConnectionStarter class
     * @param hostname HostName
     * @param port Port Number
     */
    public ClientServerConnectionStarter(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }
    
    /**
     * run()
     * Main thread execution procedures
     */
    @Override
    public void run() {
        System.out.println("Connecting to " + this.hostname + ":" + this.port + "...");
        try {
            Socket serverConnection = new Socket(this.hostname, this.port);
            System.out.println("Connected!");
            // Start input listener
            ClientInputListener listener = 
                    new ClientInputListener(serverConnection.getOutputStream());
            listener.start();
            // Start ClientServerConnector
            ClientServerConnector serverConnector = 
                    new ClientServerConnector(serverConnection);
            serverConnector.start();
        }
        catch(IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
