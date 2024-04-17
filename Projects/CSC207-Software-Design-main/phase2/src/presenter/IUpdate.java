package presenter;


/**
 * This interface is specifically used to reverse the dependency when the presenter wants to update the
 * view.
 */
public interface IUpdate {
    /**
     * update UI
     * @param action the action(instruction) about how to update
     */
    public void update(String action);
}
