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
     * changeSeeChatStatus()
     * Changes the user's ability to receive chats
     * @param status True if they can see chats, False if not
     */
    public void changeSeeChatStatus(boolean status) {
        this.canSeeChat = status;
    }
    
    /**
     * changeTalkStatus()
     * Changes the user's ability to talk
     * @param status True if they can talk, False if not
     */
    public void changeTalkStatus(boolean status) {
        this.canTalk = status;
    }
    
    /**
     * changeVoteStatus()
     * Changes the user's ability to vote
     * @param status True if they can vote, False if not
     */
    public void changeVoteStatus(boolean status) {
        this.canVote = status;
    }
    
    /**
     * deactivate()
     * Deactivates the user
     */
    public void deactivate() {
        this.isAlive = false;
    }
    
    /**
     * isAlive()
     * Tells if the user is alive
     * @returns True if Yes, False if No
     */
    public boolean isAlive() {
        return this.isAlive;
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
