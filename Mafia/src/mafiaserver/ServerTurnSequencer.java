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
    Map<String, Integer> sheriffVotes;
    Map<String, Integer> mafiaVotes;
    Map<String, Integer> publicVotes;

    /**
     * ServerTurnSequencer() Constructor for ServerTurnSequencer
     * @param ms
     */
    public ServerTurnSequencer(MafiaServer ms) {
        this.turnController = new ServerTurnController(this);
        this.server = ms;
    }

    @Override
    public synchronized void run() {
        
        // inform everyone of their roles
        for(Participant p : this.getClients())
        {
            p.notifyOfRole();
        }
        
        double minutesPerTurn = 1;
        long msPerTurn = (long) (minutesPerTurn * 60000);

        while (!turnController.isGameOver()) {
            
            // empty votes
            this.server.publicVotes.clearVotes();
            this.server.mafiaVotes.clearVotes();
            this.server.sheriffVotes.clearVotes();
            
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
            
            // processing of votes should go here
            
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

}
