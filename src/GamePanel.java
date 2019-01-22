import java.awt.*;
import java.io.*;
import javax.swing.*;

public class GamePanel extends JPanel {

    private BoardPanel bp;
    private JPanel bpWrapper;
    
    public GamePanel(StateManager stateManager) {
        this.setLayout(new BorderLayout());

        setBackground(new Color(100, 150, 170));

        // Declarations
        JMenuItem iQuit, iNew, iSave, iLoad, iHelp, iEndGame;
        JMenu mFile, mOption;
        JMenuBar jMenuBar;

        /* Initialise board wrapper */
        bpWrapper = new JPanel();
        bpWrapper.setLayout(new GridBagLayout());
        bpWrapper.setBackground(new Color(100, 150, 170));
        /* End init */

        /* Initialise JMenuBar */
        jMenuBar = new JMenuBar();

        mFile = new JMenu("Game");
        mOption = new JMenu("Option");

        iNew = new JMenuItem("New Game");
        iNew.addActionListener(e -> makeNewGame());
        mFile.add(iNew);
        
        iSave = new JMenuItem("Save");
        iSave.addActionListener(e -> save());
        mFile.add(iSave);
        
        iLoad = new JMenuItem("Load");
        iLoad.addActionListener(e -> load());
        mFile.add(iLoad);

        mFile.addSeparator();

        iQuit = new JMenuItem("Exit Game");
        iQuit.addActionListener(e -> stateManager.changeToState(StateManager.ID.MAIN_MENU));
        mFile.add(iQuit);
        
        iHelp = new JMenuItem("Help");
        iHelp.addActionListener(e -> stateManager.changeToState(StateManager.ID.INSTRUCTIONS));
        iHelp.setActionCommand("HELP");
        mOption.add(iHelp);

        iEndGame = new JMenuItem("End Game");
        iEndGame.addActionListener(e -> setEnd());
        mOption.add(iEndGame);
        
        jMenuBar.add(mFile);
        jMenuBar.add(mOption);
        /* End JMenuBar init */

        this.add(bpWrapper, BorderLayout.CENTER);
        this.add(jMenuBar, BorderLayout.PAGE_START);

        this.setPreferredSize(new Dimension(900, 700));

        this.setFocusable(true);
    }

    private void makeNewGame() {
        String[] options = new String[]{ "Player" };
        JLabel label = new JLabel("New Game Options");
        JComboBox<String> cbBlack = new JComboBox<>(options), cbWhite = new JComboBox<>(options);
        JComponent[] inputs = new JComponent[] { label, cbBlack, cbWhite };

        int result = JOptionPane.showConfirmDialog(this, inputs, "New Game", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            // Set board panel
            setBP(new BoardPanel(cbBlack.getSelectedItem().equals("Player"), cbWhite.getSelectedItem().equals("Player")));
        }
    }
    /**
     * Save boardPanel to file
     */
    private void save() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("save"));

        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fSave = fc.getSelectedFile();
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fSave));
                out.writeObject(bp);
                out.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Load boardPanel from file
     */
    private void load() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("save"));

        try {
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(fc.getSelectedFile()));
                this.setBP((BoardPanel) in.readObject());
                in.close();

                synchronized (this) {
                    notify();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private void setEnd() {
    	if (bp != null)
    		bp.setGameOver(true);
    }
    
    public void setBP(BoardPanel bp) {
        this.bp = bp;
        bpWrapper.removeAll();
        bpWrapper.add(this.bp);
        bpWrapper.validate();
    }

}
