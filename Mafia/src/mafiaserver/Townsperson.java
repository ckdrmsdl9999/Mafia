package mafiaserver;

/**
 * Townsperson.java
 * Contains the Townsperson class
 * @author Ryan Snell <ryansnell@me.com>
 */
public class Townsperson extends Role {
    
    public Townsperson() {
        this.isMafia = false;
        this.name = "Townsperson";
    }
    
    /**
     * doAction()
     */
    @Override
    public void doAction() {
    }
}
