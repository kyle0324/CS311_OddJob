package com.example.wardenfront;

/**
 * @author Ayden Boehme
 *
 * A custom CardView model made for the Inbox's
 * RecyclerView. Each new message that is updated onto the Recycler
 * gets its own InboxMessageCard_Model that contains the message's
 * topic.
 */
public class InboxMessageCard_Model {

    private String messageTopic;
    private String message;
    private String ID;

    // Constructor
    public InboxMessageCard_Model(String messageTopic, String message, String ID) {
        this.messageTopic = messageTopic;
        this.message = message;
        this.ID = ID;
    }

    // Getter and Setter
    public String getInboxMessageTopic() {
        return this.messageTopic;
    }
    public void setInboxMessageTopic(String messageTopic) {
        this.messageTopic = messageTopic;
    }

    public String getInboxMessage() {
        return this.message;
    }
    public void setInboxMessage(String message) {
        this.message = message;
    }

    public String getInboxID() {
        return this.ID;
    }
    public void setInboxID(String ID) {
        this.ID = ID;
    }

}
