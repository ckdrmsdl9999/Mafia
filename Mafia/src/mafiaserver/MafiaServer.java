package mafiaserver;

import java.util.Vector;

/**
 * MafiaServer.java
 * Contains the MafiaServer class
 * @author Cory Gehr (cmg5573)
 */
public class MafiaServer extends Thread {
    
    public Vector<Participant> clients; // Client list
    public final int port;              // Port Number
    
    /**
     * MafiaServer()
     * Constructor for the MafiaServer class
     * @param port Port Number
     */
    public MafiaServer(int port) {
        this.clients = new Vector();
        this.port = port;
    }
    
    /**
     * main()
     * Entry Point for the MafiaServer application
     * @param args Command Line arguments
     */
    public static void main(String[] args) {
        // Create server thread (arg 0 is port number)
        MafiaServer server = new MafiaServer(Integer.parseInt(args[0]));
        server.start();
    }
    
    /**
     * getClientList()
     * Returns a string with all client usernames
     * @return String with Client usernames
     */
    public String getClientList() {
        String list = "";
        return this.clients.stream().map((user) -> user.getUsername() + "\n").reduce(list, String::concat);
    }
    
    /**
     * run()
     * Main thread execution procedures
     */
    @Override
    public void run() {
        // Start remote connection threads
        ServerConnectionListener listener = new ServerConnectionListener(this);
        listener.start();
        
        // Add current server to the list of clients
        ServerParticipant serverClient = new ServerParticipant("(SERVER)");
        this.clients.add(serverClient);
        
        // Create a client listener
        ServerClientConnector clientConnector = 
                new ServerClientConnector(serverClient, this);
        clientConnector.start();
        
        // Create turn sequencer
        ServerTurnSequencer turnSequencer = new ServerTurnSequencer();
        turnSequencer.start();
    }
}