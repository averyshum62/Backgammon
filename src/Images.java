import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Images {

    /* Image keys */
    public enum k {
        BOARD,
        BOARD_TEXT_BLACK,
        BOARD_TEXT_WHITE,
        DIE_1,
        DIE_2,
        DIE_3,
        DIE_4,
        DIE_5,
        DIE_6,
        ROLL_BUTTON,
        STONE_BLACK,
        STONE_WHITE,
        STONE_RING_HIGHLIGHT,
        PIP_BLACK,
        PIP_WHITE,
        PIP_HIGHLIGHT_HARD,
        PIP_HIGHLIGHT_SOFT,
        HOME_HIGHLIGHT
    }

    /* Image dimensions */
    public static final int
        PIP_WIDTH = 50,
        PIP_HEIGHT = 210,
        BOARD_WIDTH = 780,
        BOARD_HEIGHT = 620,
        DIE_WIDTH = 32,
        DIE_HEIGHT = 32,
        ROLL_BUTTON_WIDTH = 96,
        ROLL_BUTTON_HEIGHT = 32,
        STONE_WIDTH = 32,
        STONE_HEIGHT = 32,
        STONE_RING_HIGHLIGHT_WIDTH = 38,
        STONE_RING_HIGHLIGHT_HEIGHT = 38;

    private static HashMap<k, BufferedImage> im = new HashMap<>();

    public static void load() {
        try {
            im.put(k.BOARD, ImageIO.read(new File("res/img/board.png")));
            im.put(k.BOARD_TEXT_BLACK, ImageIO.read(new File("res/img/board_text_black.png")));
            im.put(k.BOARD_TEXT_WHITE, ImageIO.read(new File("res/img/board_text_white.png")));
            im.put(k.DIE_1, ImageIO.read(new File("res/img/die_1.png")));
            im.put(k.DIE_2, ImageIO.read(new File("res/img/die_2.png")));
            im.put(k.DIE_3, ImageIO.read(new File("res/img/die_3.png")));
            im.put(k.DIE_4, ImageIO.read(new File("res/img/die_4.png")));
            im.put(k.DIE_5, ImageIO.read(new File("res/img/die_5.png")));
            im.put(k.DIE_6, ImageIO.read(new File("res/img/die_6.png")));
            im.put(k.ROLL_BUTTON, ImageIO.read(new File("res/img/roll_button.png")));
            im.put(k.STONE_BLACK, ImageIO.read(new File("res/img/stone_black.png")));
            im.put(k.STONE_WHITE, ImageIO.read(new File("res/img/stone_white.png")));
            im.put(k.STONE_RING_HIGHLIGHT, ImageIO.read(new File("res/img/stone_ring_highlight.png")));
            im.put(k.PIP_BLACK, ImageIO.read(new File("res/img/pip_black.png")));
            im.put(k.PIP_WHITE, ImageIO.read(new File("res/img/pip_white.png")));
            im.put(k.PIP_HIGHLIGHT_HARD, ImageIO.read(new File("res/img/pip_highlight_hard.png")));
            im.put(k.PIP_HIGHLIGHT_SOFT, ImageIO.read(new File("res/img/pip_highlight_soft.png")));
            im.put(k.HOME_HIGHLIGHT, ImageIO.read(new File("res/img/home_highlight.png")));
        } catch (IOException ioe) {
            System.err.println(ioe.toString());
        }
    }

    public static BufferedImage getImage(k key) {
        return im.get(key);
    }
}