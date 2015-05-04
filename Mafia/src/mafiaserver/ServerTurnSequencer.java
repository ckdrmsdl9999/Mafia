package mafiaserver;

import java.util.Map;
import java.util.Vector;

/**
 * ServerTurnSequencer.java Contains the ServerTurnSequencer class
 *
 * @author Ryan Snell <ryansnell@me.com>
 */
public class ServerTurnSequencer extends Thread {
    
    ServerTurnController turnController;
    MafiaServer server;

    /**
     * ServerTurnSequencer() Constructor for ServerTurnSequencer
     * @param ms
     */
    public ServerTurnSequencer(MafiaServer ms) {
        this.turnController = new ServerTurnController(this);
        this.server = ms;
    }

    @Override
    public void run() {
        
        // inform everyone of their roles
        for(Participant p : this.getClients())
        {
            p.notifyOfRole();
        }
        
        double minutesPerTurn = 0.4;
        long msPerTurn = (long) (minutesPerTurn * 60000);
        String publicVoteResult, mafiaVoteResult;

        while (!turnController.isGameOver()) {
            
            // empty votes
            this.server.publicVotes.clearVotes();
            this.server.mafiaVotes.clearVotes();
            
            // schedule a new turn every 5 minutes
            if (this.turnController.isDayTime()) {
                this.turnController.daySequence();
            } else {
                this.turnController.nightSequence();
            }
            try {
                Thread.sleep(msPerTurn);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }

            publicVoteResult = this.server.publicVotes.getResult();
            mafiaVoteResult = this.server.mafiaVotes.getResult();
            
            if(publicVoteResult != null) {
                Participant playerToLynch = this.server.lookupParticipantByUsername(publicVoteResult);
                if(playerToLynch != null) {
                    playerToLynch.deactivate();
                    synchronized(this.getClients()) {
                        String msg = "(NARRATOR): " + playerToLynch.getUsername() + " has lynched by the public";
                        for(Participant p : this.getClients()) {
                            synchronized(p) {
                                p.pushOutput(msg);
                            }
                        }
                        this.server.outputToLog(msg);
                    }
                }

            }
            
            if(mafiaVoteResult != null) {
                Participant nextHit = this.server.lookupParticipantByUsername(mafiaVoteResult);
                if(nextHit != null) {
                    nextHit.deactivate();
                    synchronized(this.getClients()) {
                       String msg = "(NARRATOR): " + nextHit.getUsername() + " has been murdered by the mafia";
                        for(Participant p : this.getClients()) {
                            synchronized(p) {
                                p.pushOutput(msg);
                            }
                        }
                        this.server.outputToLog(msg);
                    }
                }
                
            }
            
        }
        
        // game over
        this.outputGameOverMessage();
        
        // close log
        this.server.closeLog();
        
        // disconnect clients
        for(Participant p : this.getClients()) {
            synchronized(p) {
                p.disconnect();
            }
        }
        
    }
    
    /**
     * getClients()
     * Get all clients
     * @return Vector of all clients
     */
    public Vector<Participant> getClients() {
        return server.getClients();
    }
    
    /**
     * outputGameOverMessage()
     * Output the relevant game over message
     */
    public void outputGameOverMessage() {
        int townspeople = 0;
        int mafia = 0;
        String message = "";
        
        for(Participant p : this.getClients()) {
            synchronized(p) {
                if(p.getRole() != null && p.isAlive()) {
                    if(p.getRole().isMafia())
                        mafia++;
                    else
                        townspeople++;
                }
            }
        }
        if(mafia == 0)
            message = "The townspeople have successfully fought off the Mafia; congratulations!";
        else
            message = "Unfortunately, the Mafia have once again taken over the town.";
        
        this.turnController.blastPrompt("The game has ended: " + message);
        this.server.outputToLog("The game has ended: " + message);
    }

}
