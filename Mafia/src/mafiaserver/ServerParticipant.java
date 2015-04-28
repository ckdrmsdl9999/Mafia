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
    }
    
    /**
     * canSeeChat()
     * Tells if the user can receive chat messages
     * @return True if Yes, False if No
     */
    @Override
    public boolean canSeeChat() {
        // Server can always see chats
        return true;
    }
    
    /**
     * canTalk()
     * Tells if the user can send messages
     * @return True if Yes, False if No
     */
    @Override
    public boolean canTalk() {
        // Server can always talk
        return true;
    }
    
    /**
     * canVote()
     * Tells if the user can vote
     * @return True if Yes, False if No
     */
    @Override
    public boolean canVote() {
        // Server can never vote
        return false;
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
     * pushOutput()
     * Pushes data to the client
     * @param input Message to send
     */
    @Override
    public void pushOutput(String input) {
        System.out.println("\b" + input);
    }
}
