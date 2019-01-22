import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InformationPanel extends JPanel {

    public InformationPanel(StateManager stateManager) {
        this.setBackground(new Color(100, 150, 170));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title;
        JTextArea textArea;
        JScrollPane scrollPane;
        JButton bBack;

        title = new JLabel("Information");
        title.setFont(new Font("Franklin Gothic", Font.BOLD, 54));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        textArea = new JTextArea();
        String textToShow = "information.txt missing";
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("res/txt/information.txt")));

            textToShow = "";

            while (br.ready()) {
                textToShow = textToShow.concat(br.readLine() + "\n");
            }
        } catch (IOException ioe) {
            System.err.println(ioe.toString());
        } finally {
            textArea.setText(textToShow);
        }
        textArea.setFont(new Font("Franklin Gothic", Font.PLAIN, 24));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(140, 190, 210));
        textArea.setMargin(new Insets(30, 30, 30, 30));
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setMaximumSize(new Dimension(900, 300));

        bBack = new JButton("Back");
        bBack.setFont(new Font("Franklin Gothic", Font.BOLD, 48));
        bBack.addActionListener(e -> stateManager.returnToPreviousState());
        bBack.setPreferredSize(new Dimension(300, 70));
        bBack.setBackground(new Color(165, 210, 230));
        bBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createVerticalGlue());
        this.add(Box.createVerticalStrut(30));
        this.add(title);
        this.add(Box.createVerticalStrut(30));
        this.add(scrollPane);
        this.add(Box.createVerticalStrut(30));
        this.add(bBack);
        this.add(Box.createVerticalStrut(30));
        this.add(Box.createVerticalGlue());
    }
}
