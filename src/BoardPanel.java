import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

/**
 * Class representing the panel where the board is drawn.
 */

public class BoardPanel extends JPanel implements Serializable, MouseListener, MouseMotionListener {

    /*
     * Variable Dictionary
     *
     * board  - board
     * winner - color of the winning player (Board.Color.NONE if game still in progress)
     */
    private Board board;
    private Board.Color winner;
    private boolean gameOver;

    /**
     * Constructs a new board panel.
     */
    public BoardPanel() {
        this(true, true);
    }

    /**
     * Constructs a new board panel. (AI functionality currently not implemented)
     *
     * @param black true if player black is player, false if player black is AI
     * @param white true if player white is player, false if player white is AI
     */
    public BoardPanel(boolean black, boolean white) {
        board = new Board(black, white);

        setLayout(new FlowLayout());

        Dimension boardSize = new Dimension(Images.BOARD_WIDTH, Images.BOARD_HEIGHT);
        setMinimumSize(boardSize);
        setMaximumSize(boardSize);
        setPreferredSize(boardSize);

        winner = Board.Color.NONE;
        gameOver = false;

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Draws this component.
     *
     * @param g the graphics environment
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameOver) {
            if (board.getGameOver()) winner = board.getWinner();

            setWinner(winner);
            drawWinScreen(g);
        } else {
            board.draw(g);
        }
    }

    /**
     * Draws the win screen.
     *
     * @param g the graphics environment
     */
    private void drawWinScreen(Graphics g) {
        g.setColor(new Color(100, 150, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        String winnerString;

        if (winner == Board.Color.BLACK)
            winnerString = "BLACK WINS!!!";
        else if (winner == Board.Color.WHITE)
            winnerString = "WHITE WINS!!!";
        else
            winnerString = "NO WINNER";

        g.setColor(Color.BLACK);
        g.setFont(new Font("Franklin Gothic", Font.ITALIC, 32));
        g.drawString(winnerString, this.getWidth() / 2 - 100, this.getHeight() / 2 + 80);

        drawDiamond(200, 110, 400, 180, g);
        drawDiamond(580, 110, 400, 180, g);
    }

    /**
     * Sets the winner of the game.
     *
     * @param winner color of the winning player
     */
    public void setWinner(Board.Color winner) {
        this.winner = winner;
    }

    /**
     * Sets whether the game is over.
     *
     * @param gameOver whether the game is over
     */
    public void setGameOver(boolean gameOver) {
       this.gameOver = gameOver;
    }

    /**
     * Recursively draws nested diamonds with concave sides.
     *
     * @param x centre x
     * @param y top y
     * @param h height of diamond
     * @param w width of diamond
     * @param g the graphics environment
     */
    private void drawDiamond(int x, int y, int h, int w, Graphics g) {
        if (h < 50 || w < 30) return;

        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(2.5f));

        g2d.drawArc(x, y - h / 2, w, h, 180, 90);
        g2d.drawArc(x, y + h / 2, w, h, 90, 90);
        g2d.drawArc(x - w, y - h / 2, w, h, 270, 90);
        g2d.drawArc(x - w, y + h / 2, w, h, 0, 90);

        drawDiamond(x, y + 34, h - 68, w - 28, g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Pass
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        board.setMousePos(e.getPoint());
        board.handleMouse();
        
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e))
            board.setMouseClick(true);
        else if (SwingUtilities.isRightMouseButton(e))
            board.handleMoveCycle();

        board.handleMouse();

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	if (SwingUtilities.isLeftMouseButton(e)) {
    		board.setMouseClick(false);
    	}
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Pass
    }

    @Override
    public void mouseExited(MouseEvent e) {
    	// Pass
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    	// Pass
    }
}