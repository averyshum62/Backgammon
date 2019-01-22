import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Dice implements Serializable {

    private static final int X = 378, Y = 294, WIDTH = 300, HEIGHT = 32;
    private static final ArrayList<ArrayList<Die>> DICE_PAIRS = generatePairs();

    private ArrayList<Die> diceList;
    private int index = 0;
    private Hitbox hitbox;

    public Dice() {
        hitbox = new Hitbox(X, Y, WIDTH, HEIGHT);

        diceList = new ArrayList<>();
    }

    public Dice(boolean random) {
        this();

        if (random)
            randomRoll();
        else
            setNextPair();
    }

    private static ArrayList<ArrayList<Die>> generatePairs() {
        ArrayList<ArrayList<Die>> output = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                ArrayList<Die> pair = new ArrayList<>();
                pair.add(new Die(i));
                pair.add(new Die(j));
                output.add(pair);
            }
        }

        return output;
    }

    public void randomRoll() {
        diceList.clear();

        int r = (int) (Math.random() * DICE_PAIRS.size());

        diceList = new ArrayList<>(DICE_PAIRS.get(r));

        // Half the time, reverse pair order
        if (Math.random() < 0.5) {
            diceList.add(diceList.remove(0));
        }

        // Double list if pair is double roll
        if (diceList.get(0).equals(diceList.get(1))) {
            diceList.addAll(2, new ArrayList<>(DICE_PAIRS.get(r)));
        }
    }

    public void setNextPair() {
        diceList = new ArrayList<>(DICE_PAIRS.get(index++ % DICE_PAIRS.size()));
    }

    /**
     * Calculates all unique dice combinations. Dice are unique if their values are different.
     *
     * @return all dice combinations.
     */
    public ArrayList<ArrayList<Die>> getAllDiceCombinations() {
        ArrayList<ArrayList<Die>> output = new ArrayList<>();

        // If only one die
        if (diceList.size() == 1) {
            output.add(new ArrayList<>(diceList));
            return output;
        }

        // If double roll
        if (diceList.get(0).equals(diceList.get(1))) {

            // Add each combination (1 to n copies of the die value)
            for (int i = diceList.size() - 1; i >= 0; i--) {
                ArrayList<Die> temp = new ArrayList<>();

                for (int j = 0; j <= i; j++) {
                    temp.add(new Die(diceList.get(j)));
                }

                output.add(temp);
            }

            return output;
        }

        // If not double roll, add combinations: {d1, d2, d1 + d2}
        ArrayList<Die> d1 = new ArrayList<>(), d2 = new ArrayList<>(), d1d2 = new ArrayList<>();

        d1.add(diceList.get(0));
        output.add(d1);

        d2.add(diceList.get(1));
        output.add(d2);

        d1d2.addAll(diceList);
        output.add(d1d2);

        return output;
    }

    /**
     * Removes the dice of the move from this list.
     *
     * @param move the move
     */
    public void removeDiceInMove(Move move) {
        for (Die die : move.getDiceToUse()) {
            diceList.remove(die);
        }
    }

    public int size() {
        return diceList.size();
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public void draw(Graphics g) {
        // Update dice if clicked
        if (hitbox.isClicked()) {
            randomRoll();
            hitbox.setClickable(false);
            hitbox.setClick(false);
        }

        // Draw all dice, centred on right half of the board
        for (int i = 0; i < diceList.size(); i++) {
    	    diceList.get(i).draw(g, X + (WIDTH - Images.DIE_WIDTH * (diceList.size() * 2 - 1)) / 2 + 2 * i * Images.DIE_WIDTH, Y);
        }

        // Draw reroll button if dice are clickable
        if (hitbox.isClickable()) {
            g.drawImage(Images.getImage(Images.k.ROLL_BUTTON), X + (WIDTH - Images.ROLL_BUTTON_WIDTH) / 2, Y,
                    Images.ROLL_BUTTON_WIDTH, Images.ROLL_BUTTON_HEIGHT, null);
        }
    }
}
