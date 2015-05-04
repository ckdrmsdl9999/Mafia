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
        if(input.startsWith("HELP")) { // Display help message
            // Output help string
            String helpText = separator + "\nALL COMMANDS:\n" 
                    + separator + "\n"
                    + "HELP = Display this message\n"
                    + "STATUS = Display game status\n"
                    + "SHOW USERS = Displays a list of all player usernames\n"
                    + "PLAYER {player} = View the status of a player\n"
                    + "ACCUSE {player} = (Night - Cop Only) Reveal a player's role\n"
                    + "MURDER {player} = (Night - Mafia Only) Murder a player\n"
                    + "VOTE {player} = (Day) Vote a player for lynching";
            client.pushOutput(helpText);
        }
        else if(input.startsWith("PLAYER")) { // Get player status
            // Get target player's username (7 is where player name starts)
            if(input.length() >= 7) {
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
            else {
                client.pushOutput("Please enter a valid vote after the PLAYER command.");
            }
        }
        else if(input.startsWith("MURDER")) { // Murder a player
            // Get target player's username (7 is where player name starts)
            if(input.length() >= 7) {
                String username = input.substring(7);
                // If player can vote
                if(client.canVote() && client.getRole().isMafia) {
                    this.serverObject.mafiaVotes.addVote(client, username);
                }
                else {
                    client.pushOutput("You cannot currently do that.");
                }
            }
            else {
                client.pushOutput("Please enter a valid vote after the MURDER command.");
            }
        }
        else if(input.startsWith("ACCUSE")) { // Accuse a player of being part of the mafia
            // Get target player's username (7 is where player name starts)
            if(input.length() >= 7) {
                String username = input.substring(7);

                // If player can vote
                if(client.canVote() && client.getRole().getName().equals("Sheriff")) {
                    // Run vote action
                    this.serverObject.sheriffVotes.addVote(client, username);
                }
                else {
                    client.pushOutput("You cannot currently do that.");
                }
            }
            else {
                client.pushOutput("Please enter a valid vote after the ACCUSE command.");
            }
        }
        else if(input.startsWith("SHOW USERS")) { // Show current user list
            client.pushOutput(separator);
            client.pushOutput("CURRENTLY CONNECTED USERS:");
            client.pushOutput(separator);
            client.pushOutput(this.serverObject.getClientList());
        }
        else if(input.equals("STATUS")) { // Get game status
            // TODO
        }
        else if(input.startsWith("VOTE")) { // Vote a player for lynching
            // Get target player's username (5 is where player name starts)
            if(input.length() >= 5) {
                String username = input.substring(5);

                // If player can vote
                if(client.canVote()) {
                    // Run vote action
                    this.serverObject.publicVotes.addVote(client, username);
                }
                else {
                    client.pushOutput("You cannot currently do that.");
                }
            }
            else {
                client.pushOutput("Please enter a valid vote after the VOTE command.");
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
