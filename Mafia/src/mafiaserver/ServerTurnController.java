package mafiaserver;

/**
 *
 * @author Ryan Snell <ryansnell@me.com>
 */
public class ServerTurnController {

    ServerTurnSequencer turnSequencer;

    private boolean gameOver = false;
    private boolean isDayTime = false;
    private int dayCount = 0;
    private int nightCount = 0;

    public ServerTurnController(ServerTurnSequencer sts) {
        this.turnSequencer = sts;
    }

    /**
     * daySequence() Run the day sequence
     */
    public void daySequence() {

        // Reenable chat for all
        for(Participant p : this.turnSequencer.getClients()) {
            synchronized(p) {
                p.changeSeeChatStatus(true);
                p.changeTalkStatus(true);
                p.changeVoteStatus(true);
            }
        }
        
        this.promptDaytime();
        this.promptTownspeople();

        this.isDayTime = false;
        this.dayCount++;
    }

    /**
     * nightSequence() Run the night sequence
     */
    public void nightSequence() {
        
        /** In order to go to the next time of day, call turnSequencer.interrupt(); **/

        this.promptNighttime();

        if (this.nightCount == 0) {
            // first night
            
            // get mafia list
            String mafiaList = "The following players are part of the mafia:\n" 
                    + this.turnSequencer.server.getMafiaList();
            
            // output mafia list to all mafia
            for(Participant p : this.turnSequencer.getClients())
            {
                if(p.hasRole() && p.isAlive())
                {
                    if(p.getRole() != null && p.getRole().isMafia())
                    {
                        p.pushOutput(mafiaList);
                    }
                }
            }
            
        } else {
            // regular night
            this.promptMafia();
            this.promptSheriff();
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
    
     /**
     * promptMafia()
     * Prompt the mafia to vote on murdering someone
     */
    public void promptMafia() {
        String prompt = "Mafia, it is your turn to nominate a person "
                + "to be killed.\nTo vote for a person, type and enter"
                + " `MURDER username`.\n";
        for(Participant p : this.turnSequencer.getClients())
        {
            synchronized(p) {
                if(p.getRole() != null && p.getRole().isMafia())
                {
                    p.changeSeeChatStatus(true);
                    p.changeTalkStatus(true);
                    p.changeVoteStatus(true);
                    p.pushOutput(prompt);
                }
                else {
                    p.changeSeeChatStatus(false);
                    p.changeTalkStatus(false);
                    p.changeVoteStatus(false);
                }
            }
        }
    }
    
    /**
     * promptSheriff()
     * Prompt the sheriff to accuse people of being members of the mafia
     */
    public void promptSheriff() {
        String prompt = "Sheriff, it is your turn to accuse a person "
                + "of being the mafia.\nTo accuse a person, type and enter"
                + " `ACCUSE username`.\n";
        for(Participant p : this.turnSequencer.getClients())
        {
            synchronized(p) {
                if(p.getRole() != null && p.getRole().getName().equals("Sheriff"))
                {
                    p.changeSeeChatStatus(true);
                    p.changeTalkStatus(true);
                    p.changeVoteStatus(true);
                    p.pushOutput(prompt);
                }
                else {
                    p.changeSeeChatStatus(false);
                    p.changeTalkStatus(false);
                    p.changeVoteStatus(false);
                }
            }
        }
    }
    
    /**
     * broadcastSheriffs()
     * Broadcast a message to all sheriffs
     * @param s Message
     */
    public void broadcastSheriffs(String s) {
        for(Participant p : this.turnSequencer.getClients())
        {
            synchronized(p) {
                if(p.getRole() != null && p.getRole().getName().equals("Sheriff"))
                {
                    p.pushOutput("(NARRATOR) " + s);
                }
            }
        }
    }
    
    /**
     * promptTownspeople()
     * Prompt the townspeople to vote for someone to be lynched
     */
    public void promptTownspeople() {
        String prompt = "Townspeople, it is your turn to accuse someone "
                + "of being part of the mafia.\nTo accuse a person, type and enter"
                + " `VOTE username`.\n";
        this.blastPrompt(prompt);
    }
    
    /**
     * promptNighttime()
     * Prompt the townspeople that it is night time
     */
    public void promptNighttime() {
        String prompt = "Night has fallen upon us.\n";
        this.blastPrompt(prompt);
    }
    
    /**
     * promptDaytime()
     * Prompt the townspeople that it is day time
     */
    public void promptDaytime() {
        String prompt = "The sun has risen.\n";
        this.blastPrompt(prompt);
    }
    
    /**
     * blastPrompt()
     * Send a prompt to all connected clients
     * @param s Message to broadcast
     */
    public void blastPrompt(String s) {
        ServerMessageBroadcaster broadcast = 
                new ServerMessageBroadcaster(
                        this.turnSequencer.server.clients.get(0), 
                        this.turnSequencer.server.clients, 
                        s);
        broadcast.start();
    }
    
}
