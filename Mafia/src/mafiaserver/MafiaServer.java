package mafiaserver;

import java.util.Vector;
import mafia.Player;

/**
 * MafiaServer.java
 * Contains the Mafia Server entry point
 * @author Cory Gehr (cmg5573)
 */
public class MafiaServer extends Thread {
    
    public Vector<ServerPlayer> players; // List of players
    public final int port;         // Port Number the server listens on
    
    /**
     * MafiaServer()
     * Constructor for the MafiaServer class
     * @param port Port Number to listen on
     */
    public MafiaServer(int port) {
        // Initialize player list
        this.players = new Vector();
        // Initialize port number
        this.port = port;
    }
    
    /**
     * main()
     * Entry point for the Mafia Game Server
     * @param args Command Line arguments
     */
    public static void main(String[] args) {
        // Start the main server thread
        MafiaServer serverThread = new MafiaServer(Integer.parseInt(args[0]));
        serverThread.start();
    }
    
    /**
     * run()
     * Main thread execution procedures
     */
    @Override
    public void run() {
        // Start client listener
        ServerClientListener clientListener = new ServerClientListener(this);
        clientListener.start();
    }
}
