import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.LinkedList;

public class StateManager extends LinkedList<StateManager.ID> {

    public enum ID { MAIN_MENU, GAME, INSTRUCTIONS }

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Constructs an empty state manager.
     */
    public StateManager() {
        super();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void changeToState(ID state) {
        if (this.peek() == state)
            return;

        this.push(state);

        propertyChangeSupport.firePropertyChange("STATE_UPDATE", 0, 1); //arbitrary values but must be different
    }

    public void returnToPreviousState() {
        this.pop();

        propertyChangeSupport.firePropertyChange("STATE_UPDATE", 0, 1); //arbitrary values but must be different
    }

}
