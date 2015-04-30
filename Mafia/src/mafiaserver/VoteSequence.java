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
        int winningVoteNums = 0;
        
        // First, tally the votes
        for(String vote : this.votes.values()) {
            if(results.containsKey(vote)) {
                results.put(vote, results.get(vote)+1);
            }
            else {
                results.put(vote, 1);
            }
            
            if(results.get(vote) > winningVoteNums) {
                winningVoteNums = results.get(vote);
                winner = vote;
            }
        }
        
        return winner;
    }
}
