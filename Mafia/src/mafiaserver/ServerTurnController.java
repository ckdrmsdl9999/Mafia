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

        System.out.println("(NARRATOR) The sun has risen.\n");
        
        this.promptTownspeople();

        this.isDayTime = false;
        this.dayCount++;
    }

    /**
     * nightSequence() Run the night sequence
     */
    public void nightSequence() {
        
        /** In order to go to the next time of day, call turnSequencer.interrupt(); **/

        System.out.println("(NARRATOR) Night has fallen upon us.\n");

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
                    if(p.getRole().isMafia())
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
        String prompt = "(NARRATOR) Mafia, it is your turn to nominate a person "
                + "to be killed.\nTo vote for a person, type and enter"
                + "`MURDER username`.\n";
        for(Participant p : this.turnSequencer.getClients())
        {
            if(p.getRole().isMafia())
            {
                p.pushOutput(prompt);
            }
        }
    }
    
    /**
     * promptSheriff()
     * Prompt the sheriff to accuse people of being members of the mafia
     */
    public void promptSheriff() {
        String prompt = "(NARRATOR) Sheriff, it is your turn to accuse a person "
                + "of being the mafia.\nTo accuse a person, type and enter"
                + "`ACCUSE username`.\n";
        for(Participant p : this.turnSequencer.getClients())
        {
            if(p.getRole().getName().equals("Sheriff"))
            {
                p.pushOutput(prompt);
            }
        }
    }
    
    /**
     * promptTownspeople()
     * Prompt the townspeople to vote for someone to be lynched
     */
    public void promptTownspeople() {
        String prompt = "(NARRATOR) Townspeople, it is your turn to accuse someone "
                + "of being part of the mafia.\nTo accuse a person, type and enter"
                + "`VOTE username`.\n";
        for(Participant p : this.turnSequencer.getClients())
        {
            p.pushOutput(prompt);
        }
    }
    
}
