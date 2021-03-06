package mafiaserver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public PrintWriter log;
    VoteSequence sheriffVotes;
    VoteSequence mafiaVotes;
    VoteSequence publicVotes;

    /**
     * MafiaServer() Constructor for the MafiaServer class
     *
     * @param port Port Number
     */
    public MafiaServer(int port) {
        this.clients = new Vector();
        this.port = port;
        this.sheriffVotes = new VoteSequence();
        this.mafiaVotes = new VoteSequence();
        this.publicVotes = new VoteSequence();
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
        // Initialize log
        try {
            this.log = new PrintWriter("MafiaLog_" + System.currentTimeMillis() + ".log.txt", "UTF-8");
            this.outputToLog("Initialized Mafia Log");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        

        // Start remote connection threads
        ServerConnectionListener listener = new ServerConnectionListener(this);
        listener.start();
        this.outputToLog("Started server connection listener thread");

        // Add current server to the list of clients
        ServerParticipant serverClient = new ServerParticipant("(NARRATOR)");
        this.clients.add(serverClient);
        this.outputToLog("Added narrator to client list");

        // Create a client listener
        ServerClientConnector clientConnector
                = new ServerClientConnector(0, this);
        clientConnector.start();
        this.outputToLog("Started server client connector thread");

        System.out.print("Waiting for " + MAX_CLIENTS + " players ...");

        while(clients.size() < (MAX_CLIENTS + 1)) {
            System.out.print(".");
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MafiaServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if(clients.size() == (MAX_CLIENTS + 1))
        {
            this.outputToLog("Required number of players reached");
            // randomly assign roles
            clients.get(1).setRole(new Sheriff());
            clients.get(2).setRole(new Townsperson());
            clients.get(3).setRole(new Mafia());
            clients.get(4).setRole(new Townsperson());
            clients.get(5).setRole(new Mafia());
            clients.get(6).setRole(new Townsperson());

            this.outputToLog("Assigned player roles");

            System.out.println(MAX_CLIENTS + " clients connected");

            // Create turn sequencer
            this.turnSequencer = new ServerTurnSequencer(this);
            this.turnSequencer.start();
            this.outputToLog("Started turn sequencer thread");
        }
        else {
            System.err.println("Only " + MAX_CLIENTS + " players are allowed; please try again.");
        }
        
    }
    
    /**
     * outputToLog()
     * Output a message to the game log
     * @param s Message
     */
    public void outputToLog(String s) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm:ss");
        synchronized(log) {
            String msg = "[" + sdf.format(date) + "] " + s;
            log.println(msg);
        }
    }
    
    /**
     * closeLog()
     * Close the log file
     */
    public void closeLog() {
        this.log.close();
    }
    
    /**
     * lookupParticipantByUsername()
     * Look up a participant by their username
     * @param u Username
     * @return Participant if result found, null if no result found
     */
    public Participant lookupParticipantByUsername(String u) {
        for(Participant p : this.clients)
        {
           if(p.getUsername().equalsIgnoreCase(u)) {
               return p;
           } 
        }
        return null;
    }
    
}
