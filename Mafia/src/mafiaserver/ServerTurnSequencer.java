package mafiaserver;

import java.util.Timer;

/**
 * ServerTurnSequencer.java Contains the ServerTurnSequencer class
 *
 * @author Ryan Snell <ryansnell@me.com>
 */
public class ServerTurnSequencer extends Thread {

    private boolean gameOver = false;
    private boolean isDayTime = false;
    private int dayCount = 0;
    private int nightCount = 0;
    Timer timer;

    /**
     * ServerTurnSequencer() Constructor for ServerTurnSequencer
     */
    public ServerTurnSequencer() {
        timer = new Timer();
    }

    /**
     * daySequence() Run the day sequence
     */
    public void daySequence() {
        this.isDayTime = true;

        this.dayCount++;
    }

    /**
     * nightSequence() Run the night sequence
     */
    public void nightSequence() {
        this.isDayTime = false;

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
        this.nightCount++;
    }

    @Override
    public synchronized void run() {
        while (!gameOver) {
            // schedule a new turn every 5 minutes
            timer.schedule(new ServerTimerTask(this), 0, 5 * 60 * 1000);
        }
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
