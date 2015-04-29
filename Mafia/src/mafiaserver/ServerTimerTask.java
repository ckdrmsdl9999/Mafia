package mafiaserver;

import java.util.TimerTask;

/**
 *
 * @author Ryan Snell <ryansnell@me.com>
 */
public class ServerTimerTask extends TimerTask {

    ServerTurnSequencer turnSequencer;

    public ServerTimerTask(ServerTurnSequencer sts) {
        this.turnSequencer = sts;
    }

    @Override
    public void run() {
        if (this.turnSequencer.isDayTime()) {
            this.turnSequencer.daySequence();
        } else {
            this.turnSequencer.nightSequence();
        }
    }

}
