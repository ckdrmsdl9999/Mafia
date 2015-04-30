package mafiaserver;

import java.util.ArrayList;

/**
 * Role.java
 * Contains the Role class
 * @author Cory Gehr (cmg5573)
 */
abstract public class Role {
    
    String name; // Role name
    public ArrayList<RemoteParticipant> targets; // Targets arraylist
    public boolean isMafia = false;
    
    /**
     * doAction()
     * Action to be performed
     */
    abstract public void doAction();
    
    /**
     * getName()
     * Gets the name of this role
     * @return Name of the Role
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * isMafia()
     * Tells if the user is a member of the Mafia
     * @return True if Yes, False if No
     */
    public boolean isMafia() {
        return this.isMafia;
    }
    
    /**
     * resetTargets()
     * Resets the targets for the action
     */
    public void resetTargets() {
        this.targets = new ArrayList();
    }
}
