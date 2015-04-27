package mafia;

/**
 * Player.java
 * Contains the Player class
 * @author Cory Gehr (cmg5573)
 */
public class Player {
 
    private final String name;     // Player Name
    private boolean alive = true;  // Alive status
    
    /**
     * Player()
     * Constructor for the Player class
     * @param name Player's Name
     */
    public Player(String name) {
        this.name = name;
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