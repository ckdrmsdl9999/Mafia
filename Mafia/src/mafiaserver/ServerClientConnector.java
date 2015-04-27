package mafiaserver;

import java.io.IOException;

/**
 * ServerClientConnector.java
 * Contains the ServerClientConnector class
 * @author Cory Gehr (cmg5573)
 */
public class ServerClientConnector extends Thread {
    
    private final ServerPlayer player;      // Player object
    private final MafiaServer serverObject; // MafiaServer object
    
    /**
     * ServerClientConnector()
     * Constructor for the ServerClientConnector class
     * @param player ServerPlayer object for the user
     * @param serverObject MafiaServer game object
     */
    public ServerClientConnector(ServerPlayer player, MafiaServer serverObject) {
        this.player = player;
        this.serverObject = serverObject;
    }
    
    /**
     * run()
     * Main thread execution procedures
     */
    @Override
    public void run() {
        // Wait for input from the user indefinitely
        while(true) {
            String input = this.player.getInput();
            
            // Parse input and do action based on that
            this.parseInput(input);
        }
    }
    
    /**
     * parseInput()
     * Determines a user's command
     * @param input Input String
     */
    public void parseInput(String input) {
        // Do something (ex. determine a vote, chat, etc.)
        switch(input) {
            default:
                // Base case is to chat
                ServerMessageBroadcaster broadcast = 
                        new ServerMessageBroadcaster(this.player, 
                                this.serverObject.players, input);
                broadcast.start();
            break;
        }
    }
}
