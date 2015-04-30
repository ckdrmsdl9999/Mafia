package mafiaserver;

/**
 * ServerClientConnector.java
 * Contains the server client connector class
 * @author Cory Gehr (cmg5573)
 */
public class ServerClientConnector extends Thread {
    
    private final MafiaServer serverObject; // MafiaServer object
    private final Participant client;    // The client we're connected to
    
    /**
     * ServerClientConnector()
     * Constructor for the ServerClientConnector class
     * @param client Participant object
     * @param serverObject IMServer Object
     */
    public ServerClientConnector(Participant client, MafiaServer serverObject) {
        this.client = client;
        this.serverObject = serverObject;
    }
    
    /**
     * run()
     * Main thread execution procedures
     */
    @Override
    public void run() {
        while(true) {
            // Wait for a message from the participant
            this.parseInput(client.getInput());
        }
    }
    
    /**
     * parseInput()
     * Determines an action to perform based on user input
     * @param input User Input
     */
    public void parseInput(String input) {
        // Separator string
        String separator = "---------------";
        // input Cases
        if(input.startsWith("ACCUSE")) { // Reveal player role
            // If player can vote
            if(client.canVote()) {
                // Run vote action
            }
            else {
                client.pushOutput("You cannot currently do that.");
            }
        }
        else if (input.equals("HELP")) { // Display help message
            // Output help string
            String helpText = separator + "\nALL COMMANDS:\n" 
                    + separator + "\n"
                    + "ACCUSE = (Night - Cop Only) Reveal a player's role\n"
                    + "HELP = Display this message\n"
                    + "MURDER {player} = (Night - Mafia Only) Murder a player\n"
                    + "NOMINATE {player} = (Day) Nominate a player to be lynched\n"
                    + "PLAYER {player} = View the status of a player\n"
                    + "PROTECT {player} = (Night - Medic Only) Protect a player from possible murder\n"
                    + "SHOW USERS = Displays a list of all player usernames\n"
                    + "STATUS = Display game status\n"
                    + "VOTE {player} = (Day) Vote a player for lynching";
            client.pushOutput(helpText);
        }
        else if(input.startsWith("PLAYER")) { // Get player status
            // Get target player's username (7 is where player name starts)
            String username = input.substring(7);
            // Track if we found a match
            boolean hit = false;
            
            // Go through player list to find status
            for(Participant player : this.serverObject.clients) {
                if(player.getUsername().equals(username)) {
                    // Set hit to true
                    hit = true;
                    String status = separator + "\n" + username + "\n" + separator
                            + "\nStatus: ";
                    if(player.isAlive()) {
                        status += "Alive";
                    }
                    else {
                        status += "Dead\nRole: \n";
                    }
                
                    client.pushOutput(status);
                }
            }
            
            // If we didn't get a match, tell the user
            if(!hit) {
                client.pushOutput("No match for that username.");
            }
        }
        else if(input.startsWith("MURDER")) { // Murder a player
            // If player can vote
            if(client.canVote()) {
                
            }
            else {
                client.pushOutput("You cannot currently do that.");
            }
        }
        else if(input.startsWith("NOMINATE")) { // Nominate a player for lynching
            // If player can vote
            if(client.canVote()) {
                // Run vote action
            }
            else {
                client.pushOutput("You cannot currently do that.");
            }
        }
        else if(input.startsWith("PROTECT")) { // Protect a player
            // If player can vote
            if(client.canVote()) {
                // Run vote action
            }
            else {
                client.pushOutput("You cannot currently do that.");
            }
        }
        else if(input.startsWith("SHOW USERS")) { // Show current user list
            client.pushOutput(separator);
            client.pushOutput("CURRENTLY CONNECTED USERS:");
            client.pushOutput(separator);
            client.pushOutput(this.serverObject.getClientList());
        }
        else if(input.equals("STATUS")) { // Get game status
            
        }
        else if(input.startsWith("VOTE")) { // Vote a player for lynching
            // If player can vote
            if(client.canVote()) {
                // Run vote action
            }
            else {
                client.pushOutput("You cannot currently do that.");
            }
        }
        else { // Broadcast message if user is allowed to chat
            if(client.canTalk()) {
                ServerMessageBroadcaster broadcast = 
                        new ServerMessageBroadcaster(this.client, 
                                this.serverObject.clients, input);
                broadcast.start();
            }
            else {
                client.pushOutput("You are not currently allowed to chat.");
            }
        }
    }
}
