package mafiaserver;

import java.net.Socket;

/**
 * Participant.java
 * Contains the Participant class
 * @author Cory Gehr (cmg5573)
 */
abstract class Participant {
    
    protected String username = "(UNKNOWN)";   // Username
    private boolean canTalk = true;            // Tells if user can chat
    private boolean canVote = false;           // Tells if user can vote
    private boolean canSeeChat = true;         // Tells if user can see chats
    private boolean isAlive = true;            // Tells if user is alive/active
    
    /**
     * getUsername()
     * Gets the participant's username
     * @return Username of this Participant
     */
    public String getUsername() {
        return this.username;
    }
   
    /**
     * canSeeChat()
     * Tells if the user can receive chat messages
     * @returns True if Yes, False if No
     */
    public boolean canSeeChat() {
        return this.canSeeChat || !this.isAlive;
    }
    
    /**
     * canTalk()
     * Tells if the user can send messages
     * @returns True if Yes, False if No
     */
    public boolean canTalk() {
        return this.canTalk && this.isAlive;
    }
    
    /**
     * canVote()
     * Tells if the user can vote
     * @returns True if Yes, False if No
     */
    public boolean canVote() {
        return this.canVote && this.isAlive;
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
