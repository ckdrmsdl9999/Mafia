package mafiaserver;

/**
 * Sheriff.java
 * Contains the Sheriff class
 * @author Ryan Snell <ryansnell@me.com>
 */
public class Sheriff extends Role {
    
    public Sheriff() {
        this.isMafia = false;
        this.name = "Sheriff";
    }
    
    /**
     * doAction()
     */
    @Override
    public void doAction() {
    }
}
