package mafiaserver;

/**
 * Participant.java Contains the Participant class
 *
 * @author Cory Gehr (cmg5573)
 */
abstract class Participant {

    protected String username = "(UNKNOWN)";   // Username
    private boolean canTalk = true;            // Tells if user can chat
    private boolean canVote = false;           // Tells if user can vote
    private boolean canSeeChat = true;         // Tells if user can see chats
    private boolean isAlive = true;            // Tells if user is alive/active
    private Role role = null;

    /**
     * getUsername() Gets the participant's username
     *
     * @return Username of this Participant
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * getRole() Gets the participant's role
     *
     * @return Role
     */
    public Role getRole() {
        return this.role;
    }
    
    /**
     * setRole() Sets the participant's role
     *
     * @return Role
     */
    public void setRole(Role r) {
        this.role = r;
    }

    /**
     * hasRole() Tells if the user has an assigned role
     *
     * @returns True if Yes, False if No
     */
    public boolean hasRole() {
        return (this.role != null);
    }

    /**
     * canSeeChat() Tells if the user can receive chat messages
     *
     * @returns True if Yes, False if No
     */
    public boolean canSeeChat() {
        return this.canSeeChat || !this.isAlive;
    }

    /**
     * canTalk() Tells if the user can send messages
     *
     * @returns True if Yes, False if No
     */
    public boolean canTalk() {
        return this.canTalk && this.isAlive;
    }

    /**
     * canVote() Tells if the user can vote
     *
     * @returns True if Yes, False if No
     */
    public boolean canVote() {
        return this.canVote && this.isAlive;
    }

    /**
     * changeSeeChatStatus() Changes the user's ability to receive chats
     *
     * @param status True if they can see chats, False if not
     */
    public void changeSeeChatStatus(boolean status) {
        this.canSeeChat = status;
    }

    /**
     * changeTalkStatus() Changes the user's ability to talk
     *
     * @param status True if they can talk, False if not
     */
    public void changeTalkStatus(boolean status) {
        this.canTalk = status;
    }

    /**
     * changeVoteStatus() Changes the user's ability to vote
     *
     * @param status True if they can vote, False if not
     */
    public void changeVoteStatus(boolean status) {
        this.canVote = status;
    }

    /**
     * deactivate() Deactivates the user
     */
    public void deactivate() {
        this.isAlive = false;
    }

    /**
     * isAlive() Tells if the user is alive
     *
     * @returns True if Yes, False if No
     */
    public boolean isAlive() {
        return this.isAlive;
    }

    /**
     * notifyOfRole() Prints the user's their role
     */
    public void notifyOfRole() {
        if (this.hasRole()) {
            this.pushOutput("Your role is " + this.role.getName() + ".\n");
        } else {
            this.pushOutput("You have not yet been assigned a role.\n");
        }
    }

    /**
     * disconnect() Disconnects the client
     */
    abstract public void disconnect();
    
    /**
     * isConnected()
     * Returns true if the client is still connected
     * @return True if Yes, False if No
     */
    abstract public boolean isConnected();
    
    /**
     * getInput() Gets data from the client
     *
     * @return Input from Client
     */
    abstract public String getInput();

    /**
     * pushOutput() Pushes data to the client
     *
     * @param input Message to send
     */
    abstract public void pushOutput(String input);
}
