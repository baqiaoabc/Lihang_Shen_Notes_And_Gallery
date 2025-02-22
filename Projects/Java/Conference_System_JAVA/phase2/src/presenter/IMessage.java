package presenter;


/**
 * This interface is specifically used to reverse the dependency when the presenter wants to update the
 * view.
 */
public interface IMessage {
    /**
     * Generate message about whether a message is sent successfully.
     * @param success
     */
    void messageSuccess(boolean success);

    /**
     * Make the panel visible
     */
    void setVisible();
}
