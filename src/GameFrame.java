/*
 * Final Project
 * Backgammon
 *
 * Runs a program which allows the user to play a simulation of backgammon. Calculates possible moves for the player.
 *
 * Avery Shum & Marco Yang
 * ICS4U1-04
 * Strelkovska
 * 26 January 2018
 */

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import javax.swing.*;

public class GameFrame extends JFrame implements PropertyChangeListener {

    private static final Dimension DIMENSIONS = new Dimension(1000, 720);
    private final StateManager STATE_MANAGER = new StateManager();

    private HashMap<StateManager.ID, JPanel> panels;

    /**
     * Constructs a new GameFrame.
     */
    public GameFrame() {
        super("Backgammon");

        Container cont = this.getContentPane();
        cont.setLayout(new GridLayout(1, 1));

        // Listen for changes to StateManager
        STATE_MANAGER.addPropertyChangeListener(e -> updateState());

        // Initialise JPanels (states)
        panels = new HashMap<>();
        panels.put(StateManager.ID.MAIN_MENU, new MainMenuPanel(STATE_MANAGER));
        panels.put(StateManager.ID.GAME, new GamePanel(STATE_MANAGER));
        panels.put(StateManager.ID.INSTRUCTIONS, new InformationPanel(STATE_MANAGER));

        // Set to main menu
        STATE_MANAGER.changeToState(StateManager.ID.MAIN_MENU);

        // Set window properties
        setMinimumSize(DIMENSIONS);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        setIconImage(Images.getImage(Images.k.STONE_WHITE));
    }

    /**
     * Updates this GameFrame's state (active view).
     */
    public void updateState() {
        Container cont = this.getContentPane();

        // Reset frame
        cont.removeAll();

        // Add desired view
        cont.add(panels.get(STATE_MANAGER.peek()));

        // Repaint frame
        cont.validate();
        cont.repaint();
    }

    /**
     * This method is called when a bound property gets changed.
     *
     * @param e the property change event
     */
    public void propertyChange(PropertyChangeEvent e) {
        updateState();
    }

    /**
     * Driver method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Images.load();

        GameFrame frame = new GameFrame();
        frame.setVisible(true);
    }
}


