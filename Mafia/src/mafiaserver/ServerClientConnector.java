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
        switch(input) {
            case "SHOW USERS":
                client.pushOutput(separator);
                client.pushOutput("CURRENTLY CONNECTED USERS:");
                client.pushOutput(separator);
                client.pushOutput(this.serverObject.getClientList());
            break;
            
            default:
                // Broadcast message
                ServerMessageBroadcaster broadcast = 
                        new ServerMessageBroadcaster(this.client, 
                                this.serverObject.clients, input);
                broadcast.start();
            break;
        }
    }
}
