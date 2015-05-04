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
    
    /**
     * getResult()
     * Gets the result of the vote
     * @return Vote Result, or NULL if there is a tie
     */
    public String getResult() {
        HashMap<String, Integer> results = new HashMap();
        
        String winner = null;
        int winnerVotes = 0;
        boolean hasTie = false;
        
        // First, tally the votes
        for(String vote : this.votes.values()) {
            // Get vote count for this entry
            int currentVotes = results.get(vote);
            
            if(results.containsKey(vote)) {
                results.put(vote, currentVotes+1);
            }
            else {
                results.put(vote, 1);
            }
            
            if(currentVotes+1 > winnerVotes) {
                // This option has a better value than the others
                winnerVotes = currentVotes+1;
                winner = vote;
                hasTie = false;
            }
            else if(currentVotes == winnerVotes) {
                // A tie exists
                hasTie = true;
            }
        }
        
        // Return null if we have a tie
        if(hasTie) {
            return null;
        }
        
        return winner;
    }
    
    /**
     * clearVotes()
     * Empty the mapping for re-use
     */
    public void clearVotes() {
        this.votes.clear();
    }
}
