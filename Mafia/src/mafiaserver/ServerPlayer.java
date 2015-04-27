package mafiaserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * ServerPlayer.java
 * Contains the ServerPlayer class
 * @author Cory Gehr (cmg5573)
 */
public class ServerPlayer extends mafia.Player {
    
    private DataInputStream inputStream = null;   // Input provider
    private DataOutputStream outputStream = null; // Output provider
    
    /**
     * ServerPlayer()
     * Constructor for the ServerPlayer class
     * @param name User Name
     */
    public ServerPlayer(String name, Socket connection) {
        super(name);
        
        try {
            this.inputStream = new DataInputStream(connection.getInputStream());
            this.outputStream = new DataOutputStream(connection.getOutputStream());
        }
        catch(IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * getInput()
     * Gets a string input from the client
     * @return Client Input
     */
    public String getInput() {
        try {
            return this.inputStream.readUTF();
        }
        catch(IOException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
    
    /**
     * pushOutput()
     * Pushes data to the client
     * @param input Message to send
     */
    public void pushOutput(String input) {
        try {
            // Write to the client
            this.outputStream.writeUTF(input);
        }
        catch(IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
