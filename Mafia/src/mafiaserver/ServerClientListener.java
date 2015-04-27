package mafiaserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ServerClientListener.java
 * Contains the ServerClientListener class
 * @author Cory Gehr (cmg5573)
 */
public class ServerClientListener extends Thread {
    
    private MafiaServer serverObject;         // Server Object
    private final int port;                   // Port to listen on 
    private ServerSocket serverSocket = null; // ServerSocket
    
    /**
     * ServerClientListener()
     * Constructor for the ServerClientListener class
     * @param serverObject MafiaServer object
     */
    public ServerClientListener(MafiaServer serverObject) {
        this.serverObject = serverObject;
        this.port = this.serverObject.port;
        
        // Open server socket
        try {
            this.serverSocket = new ServerSocket(port);
        }
        catch(IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * run()
     * Main thread execution procedures
     */
    @Override
    public void run() {
        // Wait for clients indefinitely
        System.out.println("Waiting for clients on Port " + this.port + "...");
        while(true) {
            try {
                // Create socket for the player
                Socket client = this.serverSocket.accept();
                System.out.println("Client connected from " 
                        + client.getRemoteSocketAddress());
                
                // TODO: Check if game is running
                // If so, return error message and disconnect client
                
                // Get client name
                DataInputStream inputStream = 
                        new DataInputStream(client.getInputStream());
                String username = inputStream.readUTF();
                
                // Create player object and add to our list
                ServerPlayer newPlayer = new ServerPlayer(username, client);
                this.serverObject.players.add(newPlayer);
                
                // Create a connector service and start it
                ServerClientConnector connector = 
                        new ServerClientConnector(newPlayer, this.serverObject);
                connector.start();
            }
            catch(IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
