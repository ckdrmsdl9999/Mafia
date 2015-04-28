package mafiaserver;

import java.util.Scanner;

/**
 * RemoteParticipant.java
 * Contains the RemoteParticipant class
 * @author Cory Gehr (cmg5573)
 */
public class ServerParticipant extends Participant{
    
    private final Scanner input; // Input scanner
    private boolean isAlive = false;
    
    /**
     * ServerParticipant()
     * Constructor for the ServerParticipant class
     * @param username Participant Username
     */
    public ServerParticipant(String username) {
        this.username = username;
        this.input = new Scanner(System.in);
    }
    
    /**
     * getInput()
     * Gets a string input from the client
     * @return Client Input
     */
    @Override
    public String getInput() {
        System.out.print(":");
        return this.input.nextLine();
    }
    
    /**
     * pushOutput()
     * Pushes data to the client
     * @param input Message to send
     */
    @Override
    public void pushOutput(String input) {
        System.out.println("\b" + input);
    }
}
