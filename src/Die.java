import java.awt.*;
import java.io.Serializable;

/**
 * Class to represent a die.
 */
public class Die implements Serializable {

    /* Variable Dictionary
     *
     * value  - point value of this die (1-6)
     * active - whether this die is currently active in the game
     * image  - associated image key
     */
    private int value;
    private boolean active;
    private Images.k image;

    /**
     * Constructs a new die with a random value from 1 to 6.
     */
    public Die() {
        this((int) (Math.random() * 6 + 1));
    }

    /**
     * Constructs a new die with the specified value.
     *
     * @param value the value
     */
    public Die(int value) {
        this.value = value;
        active = true;
        setImage();
    }

    public Die(Die other) {
        this.value = other.value;
        active = true;
        setImage();
    }

    /**
     * Gets the value of this die.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the active state of this die.
     *
     * @param active state to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Checks if this die is active.
     *
     * @return active state of the die
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the image key associated with this die.
     */
    private void setImage() {
        switch (value) {
            case 1:
                image = Images.k.DIE_1;
                break;
            case 2:
                image = Images.k.DIE_2;
                break;
            case 3:
                image = Images.k.DIE_3;
                break;
            case 4:
                image = Images.k.DIE_4;
                break;
            case 5:
                image = Images.k.DIE_5;
                break;
            case 6:
                image = Images.k.DIE_6;
                break;
        }
    }

    /**
     * Draws this die at the specified position.
     *
     * @param g graphics to use
     * @param x upper-left x
     * @param y upper-left y
     */
    public void draw(Graphics g, int x, int y) {
        g.drawImage(Images.getImage(image), x, y, Images.DIE_WIDTH, Images.DIE_HEIGHT, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Die die = (Die) o;

        return value == die.value;
    }

}
