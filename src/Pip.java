import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;

public class Pip implements Serializable {

    public static final int BLACK_HOME = 25, WHITE_HOME = 0, BLACK_BAR = -1, WHITE_BAR = 26;
    
    private int number, drawY, drawHeight;
    private int x, y, w = Images.PIP_WIDTH, h = Images.PIP_HEIGHT;

    private LinkedList<Stone> stones;

    private boolean isPointingUp, highlightStart, highlightIntermediate, highlightEnd;
    private Images.k imageMain, imageHighlightHard, imageHighlightSoft;
    private Hitbox hitbox;

    public Pip() {
        this(Integer.MIN_VALUE);
    }

    public Pip(int number) {
        this.number = number;

        this.stones = new LinkedList<>();
        this.highlightStart = false;
        this.highlightIntermediate = false;
        this.highlightEnd = false;

        // Update orientation
        if (BLACK_BAR <= number && number <= 12)
            isPointingUp = true;
        else if (13 <= number && number <= WHITE_BAR)
            isPointingUp = false;

        // Set image keys
        imageMain = null;
        imageHighlightHard = null;
        imageHighlightSoft = null;

        if (number == BLACK_HOME || number == WHITE_HOME) {
            imageHighlightHard = Images.k.HOME_HIGHLIGHT;
        }

        else if (1 <= number && number <= 24) {
            if (number % 2 == 1)
                imageMain = Images.k.PIP_BLACK;
            else
                imageMain = Images.k.PIP_WHITE;

            imageHighlightHard = Images.k.PIP_HIGHLIGHT_HARD;
            imageHighlightSoft = Images.k.PIP_HIGHLIGHT_SOFT;
        }

        // Update position
        updateHitbox();
    }

    public void addStone(Stone stone) {
        stones.add(stone);
    }

    public Stone popStone() {
        return stones.pop();
    }

    public void setHighlightStart(boolean set) {
        highlightStart = set;
    }

    public void setHighlightIntermediate(boolean set) {
        highlightIntermediate = set;
    }

    public void setHighlightEnd(boolean set) {
        highlightEnd = set;
    }
    
    public boolean isPossibleStart(Board.Color color) {
    	return stones.size() >= 1 && stones.peek().getColor() == color;
    }
    
    public boolean isPossibleEnd(Board.Color color) {
        return stones.size() <= 1 || color == stones.peek().getColor();
    }
    
    private void updateHitbox() {
        // Escape conditions
        if (number < BLACK_BAR || WHITE_BAR < number)
            return;

        // Set x
        if (number == BLACK_HOME || number == WHITE_HOME)
            x = 704;
        else if (number == BLACK_BAR || number == WHITE_BAR)
            x = 327;
        else if (number <= 6)
            x = 378 + 50 * (6 - number);
        else if (number <= 12)
            x = 26 + 50 * (12 - number);
        else if (number <= 18)
            x = 26 + 50 * (number - 13);
        else // 19-24
            x = 378 + 50 * (number - 19);

        // Set y and drawHeight
        if (isPointingUp) {
            y = 384 - (number == BLACK_BAR ? 25 : 0);
            drawY = y;
            drawHeight = h;
        } else {
            y = 26 + (number == WHITE_BAR ? 25 : 0);
            drawY = y + h;
            drawHeight = -h;
        }

        // Make the hitbox
        hitbox = new Hitbox(x, y, w, h);
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public int getNumber() {
        return number;
    }

    public int getStoneSize() {
        return stones.size();
    }

    public Board.Color getColor() {
        if (stones.isEmpty())
            return Board.Color.NONE;
        return stones.peek().getColor();
    }

    public void draw(Graphics g) { //draws relative to the board
        // Draw pip
        g.drawImage(Images.getImage(imageMain), x, drawY, w, drawHeight, null);

        // Draw hard highlight filter if hovered over or end pip
        if (hitbox.isHovered() || highlightEnd)
            g.drawImage(Images.getImage(imageHighlightHard), x, drawY, w, drawHeight, null);

        // Draw soft highlight filter if intermediate pip
        else if (highlightIntermediate)
            g.drawImage(Images.getImage(imageHighlightSoft), x, drawY, w, drawHeight, null);

        // Draw stones on pip, from base to tip
        int stoneY = drawY + drawHeight;

        for (int i = 0; i < stones.size(); i++) {
            stones.get(i).draw(g, x + (Images.PIP_WIDTH - Images.STONE_WIDTH) / 2, stoneY,
                    drawHeight > 0, i == stones.size() - 1 && highlightStart);
            stoneY -= (drawHeight - (drawHeight > 0 ? 1 : -1) * Images.STONE_HEIGHT) / (stones.size() <= 6 ? 6 : stones.size());
        }
    }
}

