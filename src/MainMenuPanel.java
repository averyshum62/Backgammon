import java.awt.*;
import javax.swing.*;

/**
 * Class representing main menu GUI.
 */

public class MainMenuPanel extends JPanel{

	/**
	 * 
	 * @param stateManager
	 */
    public MainMenuPanel(StateManager stateManager) {
        this.setBackground(new Color(100, 150, 170));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title, subtitle;
        JButton bPlay, bHelp, bQuit;

        title = new JLabel("Backgammon");
        title.setFont(new Font("Franklin Gothic", Font.BOLD, 72));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtitle = new JLabel("by Avery Shum & Marco Yang");
        subtitle.setFont(new Font("Franklin Gothic", Font.PLAIN, 54));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        bPlay = new JButton("Play");
        bPlay.setFont(new Font("Franklin Gothic", Font.BOLD, 48));
        bPlay.addActionListener(e -> stateManager.changeToState(StateManager.ID.GAME));
        bPlay.setPreferredSize(new Dimension(300, 80));
        bPlay.setBackground(new Color(165, 210, 230));
        bPlay.setAlignmentX(Component.CENTER_ALIGNMENT);

        bHelp = new JButton("Instructions");
        bHelp.setFont(new Font("Franklin Gothic", Font.BOLD, 48));
        bHelp.addActionListener(e -> stateManager.changeToState(StateManager.ID.INSTRUCTIONS));
        bHelp.setPreferredSize(new Dimension(300, 70));
        bHelp.setBackground(new Color(165, 210, 230));
        bHelp.setAlignmentX(Component.CENTER_ALIGNMENT);

        bQuit = new JButton("Quit");
        bQuit.setFont(new Font("Franklin Gothic", Font.BOLD, 48));
        bQuit.addActionListener(e -> System.exit(0));
        bQuit.setPreferredSize(new Dimension(300, 70));
        bQuit.setBackground(new Color(165, 210, 230));
        bQuit.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(subtitle);
        this.add(Box.createVerticalStrut(30));
        this.add(bPlay);
        this.add(Box.createVerticalStrut(15));
        this.add(bHelp);
        this.add(Box.createVerticalStrut(15));
        this.add(bQuit);
        this.add(Box.createVerticalGlue());

        this.setMinimumSize(new Dimension(500, 500));
    }
}



