package mafiaserver;

/**
 * Mafia.java
 * Contains the Mafia class
 * @author Cory Gehr (cmg5573)
 */
public class Mafia extends Role {
    
    private final String name = "Mafia";
    
    /**
     * doAction()
     * Kills the target
     */
    @Override
    public void doAction() {
        targets.stream().forEach((target) -> {
            target.deactivate();
        });
    }
}
