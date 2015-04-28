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
            String inFromClient = client.getInput();
            String separator = "---------------";

            if(inFromClient.equals("SHOW USERS")) {
                client.pushOutput(separator);
                client.pushOutput("CURRENTLY CONNECTED USERS:");
                client.pushOutput(separator);
                client.pushOutput(this.serverObject.getClientList());
            }
            else {
                // Broadcast message
                ServerMessageBroadcaster broadcast = 
                        new ServerMessageBroadcaster(this.client, 
                                this.serverObject.clients, inFromClient);
                broadcast.start();
            }
        }
    }
}
