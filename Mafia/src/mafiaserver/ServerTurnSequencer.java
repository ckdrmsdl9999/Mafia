package mafiaserver;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ServerTurnSequencer.java Contains the ServerTurnSequencer class
 *
 * @author Ryan Snell <ryansnell@me.com>
 */
public class ServerTurnSequencer extends Thread {
    
    ServerTurnController turnController;

    /**
     * ServerTurnSequencer() Constructor for ServerTurnSequencer
     * @param stc
     */
    public ServerTurnSequencer() {
        this.turnController = new ServerTurnController(this);
    }

    @Override
    public synchronized void run() {
        
        double minutesPerTurn = 1;
        long msPerTurn = (long) (minutesPerTurn * 60000);

        while (!turnController.isGameOver()) {
            // schedule a new turn every 5 minutes
            if (this.turnController.isDayTime()) {
                this.turnController.daySequence();
            } else {
                this.turnController.nightSequence();
            }
            try {
                Thread.sleep(msPerTurn);
            } catch (InterruptedException ex) {
            }
        }
        
    }

}
