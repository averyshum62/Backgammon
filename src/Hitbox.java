import java.awt.*;
import java.io.Serializable;

/**
 * Class representing all game objects with hitboxes. 
 */

public class Hitbox extends Rectangle implements Serializable {

	/*
	 * Variable Dictionary
	 * 
	 * hover     - if hitbox is being hovered over by mouse
	 * click     - if hitbox is being clicked by mouse
	 * hoverable - if hitbox is hoverable
	 * clickable - if hitbox is clickable
	 */
    private boolean hover = false, click = false, hoverable = false, clickable = false;

    /**
     * Constructs a new hitbox object with the specified dimensions.
     * 
     * @param x x-position of hitbox
     * @param y y-position of hitbox
     * @param w width of hitbox
     * @param h height of hitbox
     */
    public Hitbox(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    /**
     * Set hover state.
     * 
     * @param hover hover state
     */
    public void setHover(boolean hover) {
        this.hover = hover;
    }

    /**
     * Set click state.
     * 
     * @param click click state
     */
    public void setClick(boolean click) {
        this.click = click;
    }

    /**
     * Set then return hover state.
     * 
     * @param p location of mouse
     * @return final hover state
     */
    public boolean checkHover(Point p) {
        setHover(this.contains(p) && hoverable);

        return hover;
    }

    /**
     * Return hover state.
     * 
     * @return hover state
     */
    public boolean isHovered() {
        return hover;
    }

    /**
     * Return click state.
     * 
     * @return final click state
     */
    public boolean isClicked() {
        return click;
    }

    /**
     * Set hoverable flag.
     * 
     * @param hoverable hoverable state
     */
    public void setHoverable(boolean hoverable) {
        this.hoverable = hoverable;
    }

    /**
     * Set clickable flag.
     * 
     * @param clickable clickable state
     */
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    /**
     * Get hoverable flag.
     * 
     * @return hoverable flag
     */
    public boolean isHoverable() {
        return hoverable;
    }

    /**
     * Get clickable flag.
     * 
     * @return clickable flag
     */
    public boolean isClickable() {
        return clickable;
    }
}
