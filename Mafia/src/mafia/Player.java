package mafia;

/**
 * Player.java
 * Contains the Player class
 * @author Cory Gehr (cmg5573)
 */
public class Player {
 
    private final int identifier;  // Unique Identifier
    private final String name;     // Player Name
    private boolean alive = true;  // Alive status
    
    /**
     * Player()
     * Constructor for the Player class
     * @param id Unique Identifier
     * @param name Player's Name
     */
    public Player(int id, String name) {
        this.identifier = id;
        this.name = name;
    }
    
    /**
     * getId()
     * Gets the unique ID for this player
     * @return Player ID
     */
    public int getId() {
        return this.identifier;
    }
    
    /**
     * getName()
     * Returns the name of this player
     * @return Name of the player
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * isAlive()
     * Tells if the player is currently alive
     * @return True if Alive, False if not
     */
    public boolean isAlive() {
        return this.alive;
    }
    
    /**
     * kill()
     * Deactivates this player
     */
    public void kill() {
        this.alive = false;
    }
}