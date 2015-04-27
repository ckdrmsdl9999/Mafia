package mafiaserver;

import java.util.Vector;

/**
 * ServerMessageBroadcaster.java
 * Contains the ServerMessageBroadcaster class
 * @author Cory Gehr (cmg5573)
 */
public class ServerMessageBroadcaster extends Thread {
    
    private final ServerPlayer source;          // Source user of message
    private final Vector<ServerPlayer> clients; // List of clients
    private final String message;              // Message to send
    
    /**
     * ServerMessageBroadcaster()
     * Constructor for the ServerMessageBroadcaster class
     * @param source Participant who created the message
     * @param clients List of Clients that should receive message
     * @param message Message to broadcast
     */
    public ServerMessageBroadcaster(ServerPlayer source, Vector<ServerPlayer> clients, String message) {
        this.source = source;
        this.clients = clients;
        this.message = source.getName() + ": " + message;
    }
    
    /**
     * run()
     * Main thread execution procedures
     */
    @Override
    public void run() {
        // Loop through all clients (that are not the current user)
        this.clients.stream().filter((user) -> (!user.getName().equals(this.source.getName()))).forEach((user) -> {
            // Output data to user
            user.pushOutput(this.message);
        });
    }
}
