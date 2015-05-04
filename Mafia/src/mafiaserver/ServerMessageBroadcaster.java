package mafiaserver;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ServerMessageBroadcaster.java
 * Contains the ServerMessageBroadcaster class
 * @author Cory Gehr (cmg5573)
 */
public class ServerMessageBroadcaster extends Thread {
    
    private final Participant source;          // Source user of message
    private final Vector<Participant> clients; // List of clients
    private final String message;              // Message to send
    
    /**
     * ServerMessageBroadcaster()
     * Constructor for the ServerMessageBroadcaster class
     * @param source Participant who created the message
     * @param clients List of Clients
     * @param message Message to broadcast
     */
    public ServerMessageBroadcaster(Participant source, Vector<Participant> clients, String message) {
        this.source = source;
        this.clients = clients;
        this.message = source.getUsername() + ": " + message;
    }
    
    /**
     * run()
     * Main thread execution procedures
     */
    @Override
    public void run() {
        // Loop through all clients (that are not the current user)
        this.clients.stream().forEach((p) -> {
            synchronized(p) {
                if(!this.source.getUsername().equals(p.getUsername()) && p.canSeeChat()) {
                    p.pushOutput(this.message);
                }
            }
        });
    }
}
