package mafiaserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ServerConnectionListener.java
 * Contains the ServerConnectionListener class
 * @author Cory Gehr (cmg5573)
 */
public class ServerConnectionListener extends Thread {
    
    private MafiaServer serverObject;         // Main server object
    private final int port;                   // Port to listen on 
    private ServerSocket serverSocket = null; // ServerSocket
    
    /**
     * ServerConnectionListener()
     * Constructor for the ServerConnectionListener class
     * @param serverObject Main server object
     */
    public ServerConnectionListener(MafiaServer serverObject) {
        this.serverObject = serverObject;
        this.port = this.serverObject.port;
        
        // Create the listener socket
        try {
            this.serverSocket = new ServerSocket(this.port);
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
        // Listen for new clients
        try {
            System.out.println("Waiting for clients on Port " + this.port 
                    + "...");
            // Run continuously
            while(true) {
                // Wait for clients
                Socket client = this.serverSocket.accept();
                System.out.println("Client connected from " 
                        + client.getRemoteSocketAddress());
                // Get client username
                DataInputStream inputStream = 
                        new DataInputStream(client.getInputStream());
                String username = inputStream.readUTF();
                // Create participant and add to client list
                Participant newUser = new RemoteParticipant(username, client);
                this.serverObject.clients.add(newUser);
                // Create a client listener
                ServerClientConnector clientConnector = 
                        new ServerClientConnector(newUser, this.serverObject);
                clientConnector.start();
                newUser.pushOutput("HELLO");
                // Broadcast user join
                ServerMessageBroadcaster broadcast = 
                        new ServerMessageBroadcaster(
                                this.serverObject.clients.get(0), 
                                this.serverObject.clients, 
                                newUser.username + " entered town.");
                broadcast.start();
            }
        }
        catch(IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
