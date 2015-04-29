package mafiaserver;

import java.util.TimerTask;

/**
 *
 * @author Ryan Snell <ryansnell@me.com>
 */
public class ServerTurnController extends Thread {

    ServerTurnSequencer turnSequencer;

    private boolean gameOver = false;
    private boolean isDayTime = false;
    private int dayCount = 0;
    private int nightCount = 0;

    public ServerTurnController(ServerTurnSequencer sts) {
        this.turnSequencer = sts;
    }

    @Override
    public void run() {
    }

    /**
     * daySequence() Run the day sequence
     */
    public void daySequence() {

        System.out.println("Day time");

        this.isDayTime = false;
        this.dayCount++;
    }

    /**
     * nightSequence() Run the night sequence
     */
    public void nightSequence() {
        
        /** In order to go to the next time of day, call turnController.interrupt(); **/

        System.out.println("Night time");

        if (this.nightCount == 0) {
            // first night
        } else {
            // regular night

            /*  Now that everyone has their identities it's time for the mafia 
             to meet each other. Everyone must close their eyes. Then tell 
             the mafia to open their eyes and reveal themselves to each 
             other. The two mafia players (the people with the queens, 
             remember?) will open their eyes and look around to see who the 
             other mafia is. When they've had enough time to do this, tell 
             them to close their eyes and have everyone open their eyes. 
             That's the first night. */
        }

        this.isDayTime = true;
        this.nightCount++;
    }

    /**
     * endGame() Mark the game as ended
     */
    public void endGame() {
        this.gameOver = true;
    }

    /**
     * isGameOver()
     *
     * @return gameOver
     */
    public boolean isGameOver() {
        return this.gameOver;
    }

    /**
     * isDayTime()
     *
     * @return isDayTime
     */
    public boolean isDayTime() {
        return this.isDayTime;
    }
}
