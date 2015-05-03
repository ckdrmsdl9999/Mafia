package mafiaserver;

import java.util.Scanner;

/**
 * RemoteParticipant.java
 * Contains the RemoteParticipant class
 * @author Cory Gehr (cmg5573)
 */
public class ServerParticipant extends Participant{
    
    private final Scanner input; // Input scanner
    
    /**
     * ServerParticipant()
     * Constructor for the ServerParticipant class
     * @param username Participant Username
     */
    public ServerParticipant(String username) {
        this.username = username;
        this.input = new Scanner(System.in);
        this.isAlive = false;
    }
    
    /**
     * disconnect() Disconnects a client
     */
    @Override
    public void disconnect() {
        // Nothing happens
    }
    
    /**
     * getInput()
     * Gets a string input from the client
     * @return Client Input
     */
    @Override
    public String getInput() {
        System.out.print(":");
        return this.input.nextLine();
    }
    
    /**
     * isConnected()
     * Returns true if the client is still connected
     * @return True if Yes, False if No
     */
    @Override
    public boolean isConnected() {
        // Server is always connected...
        return true;
    }
    
    /**
     * pushOutput()
     * Pushes data to the client
     * @param input Message to send
     */
    @Override
    public void pushOutput(String input) {
        System.out.println("\b" + input);
    }
}
