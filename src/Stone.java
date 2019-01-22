import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Stone implements Serializable {
    private Board.Color color;
    private BufferedImage image;

    public Stone(Board.Color color) {
        this.color = color;

        // Set image
        if (color.equals(Board.Color.BLACK))
            image = Images.getImage(Images.k.STONE_BLACK);
        else
            image = Images.getImage(Images.k.STONE_WHITE);
    }
    
    public Board.Color getColor(){
    	return color;
    }

    public void draw(Graphics g, int x, int y, boolean up, boolean highlighted) { // up is true if pip is pointing up
        if (highlighted)
            g.drawImage(Images.getImage(Images.k.STONE_RING_HIGHLIGHT), x - 3, y + (up ? 3 : -3),
                    Images.STONE_RING_HIGHLIGHT_WIDTH, (up ? -1 : 1) * Images.STONE_RING_HIGHLIGHT_HEIGHT, null);
        g.drawImage(image, x, y, Images.STONE_WIDTH, (up ? -1 : 1) * Images.STONE_HEIGHT, null);
    }
}
