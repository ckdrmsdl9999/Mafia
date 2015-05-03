package mafiaserver;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MafiaServer.java Contains the MafiaServer class
 *
 * @author Cory Gehr (cmg5573)
 */
public class MafiaServer extends Thread {

    private final int MAX_CLIENTS = 6;           // Maximum accepted clients
    public Vector<Participant> clients;          // Client list
    public static final int PORT_NUMBER = 65004; // Port Number
    public ServerTurnSequencer turnSequencer;    // Turn sequencer
    public int port;

    /**
     * MafiaServer() Constructor for the MafiaServer class
     *
     * @param port Port Number
     */
    public MafiaServer(int port) {
        this.clients = new Vector();
        this.port = port;
    }

    /**
     * main() Entry Point for the MafiaServer application
     *
     * @param args Command Line arguments
     */
    public static void main(String[] args) {
        // Create server thread (arg 0 is port number)
        MafiaServer server = new MafiaServer(PORT_NUMBER);
        server.start();
    }

    /**
     * getClientList() Returns a string with all client usernames
     *
     * @return String with Client usernames
     */
    public String getClientList() {
        String list = "";
        return this.clients.stream().map((user) -> user.getUsername() 
                + "\n").reduce(list, String::concat);
    }

    /**
     * getMafiaList() Returns a string with all Mafia usernames
     *
     * @return String with Mafia usernames
     */
    public String getMafiaList() {
        String list = "";
        list = this.clients.stream().filter((p) -> (p.hasRole() 
                && p.isAlive())).filter((p) -> (p.getRole().isMafia())).map((p) 
                -> p.getUsername() + "\n").reduce(list, String::concat);
        return list;
    }

    /**
     * getClients() Returns a vector of all clients
     *
     * @return Vector of all clients
     */
    public Vector<Participant> getClients() {
        return this.clients;
    }

    /**
     * run() Main thread execution procedures
     */
    @Override
    public void run() {
        // Start remote connection threads
        ServerConnectionListener listener = new ServerConnectionListener(this);
        listener.start();

        // Add current server to the list of clients
        ServerParticipant serverClient = new ServerParticipant("(NARRATOR)");
        this.clients.add(serverClient);

        // Create a client listener
        ServerClientConnector clientConnector
                = new ServerClientConnector(serverClient, this);
        clientConnector.start();
        
        System.out.print("Waiting for " + MAX_CLIENTS + " players ...");
        while(clients.size() < 6) {
            System.out.print(".");
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MafiaServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(clients.size() == MAX_CLIENTS)
        {
            // randomly assign roles
            // TODO: Make Random
            clients.get(0).setRole(new Sheriff());
            clients.get(1).setRole(new Townsperson());
            clients.get(2).setRole(new Mafia());
            clients.get(3).setRole(new Townsperson());
            clients.get(4).setRole(new Mafia());
            clients.get(5).setRole(new Townsperson());

            System.out.println(MAX_CLIENTS + " clients connected");

            // Create turn sequencer
            this.turnSequencer = new ServerTurnSequencer(this);
            this.turnSequencer.start();
        }
        else {
            System.err.println("Only " + MAX_CLIENTS + " players are allowed; please try again.");
        }
        
    }
}
