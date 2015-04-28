package mafiaserver;

import java.net.Socket;

/**
 * Participant.java
 * Contains the Participant class
 * @author Cory Gehr (cmg5573)
 */
abstract class Participant {
    
    protected String username = "(UNKNOWN)";   // Username
    
    /**
     * getUsername()
     * Gets the participant's username
     * @return Username of this Participant
     */
    public String getUsername() {
        return this.username;
    }
    
    /**
     * getInput()
     * Gets data from the client
     * @return Input from Client
     */
    abstract public String getInput();
    
    /**
     * pushOutput()
     * Pushes data to the client
     * @param input Message to send
     */
    abstract public void pushOutput(String input);
}
