package mafiaserver;

import java.util.ArrayList;

/**
 * Role.java
 * Contains the Role class
 * @author Cory Gehr (cmg5573)
 */
abstract public class Role {
    
    private String name; // Role name
    public ArrayList<RemoteParticipant> targets; // Targets arraylist
    
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
     * resetTargets()
     * Resets the targets for the action
     */
    public void resetTargets() {
        this.targets = new ArrayList();
    }
}
