package mafiaserver;

import java.util.HashMap;

/**
 * VoteSequence.java
 * Contains the VoteSequence class
 * @author Cory Gehr (cmg5573)
 */
public class VoteSequence extends Thread {
    
    private final HashMap<String, String> votes; // List of votes
    
    /**
     * VoteSequence()
     * Constructor for the VoteSequence class
     */
    public VoteSequence() {
        this.votes = new HashMap();
    }
    
    /**
     * addVote()
     * Adds a vote for a player
     * @param client Player Voting
     * @param vote Vote Value
     */
    public void addVote(RemoteParticipant client, String vote) {
        // Add or overwrite current vote for the client, if they can vote
        if(client.canVote()) {
            this.votes.put(client.getUsername(), vote);
        }
        else {
            client.pushOutput("You cannot vote on this.");
        }
    }
}
