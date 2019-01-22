import java.awt.*;
import java.io.Serializable;
import java.util.*;

/**
 * Class to contain all pips of the board.
 */
public class AllPips implements Serializable {

    /* Variable Dictionary
     *
     * pips          - list of pips (includes bars, homes, and main pips)
     * possibleMoves - list of possible moves within the pips
     */
    private HashMap<Integer, Pip> pips = new HashMap<>();
    private HashMap<Pip, HashSet<Move>> possibleMoves = new HashMap<>();

    /**
     * Constructs a new object.
     */
    public AllPips() {
        /* Main pips */
        for (int i = 1; i <= 24; i++)
            pips.put(i, new Pip(i));

        // Place starting stones
        for (int j = 0; j < 2; j++) {
            pips.get(1).addStone(new Stone(Board.Color.BLACK));
            pips.get(24).addStone(new Stone(Board.Color.WHITE));
        }
        for (int j = 0; j < 3; j++) {
            pips.get(8).addStone(new Stone(Board.Color.WHITE));
            pips.get(17).addStone(new Stone(Board.Color.BLACK));
        }
        for (int j = 0; j < 5; j++) {
            pips.get(6).addStone(new Stone(Board.Color.WHITE));
            pips.get(12).addStone(new Stone(Board.Color.BLACK));
            pips.get(13).addStone(new Stone(Board.Color.WHITE));
            pips.get(19).addStone(new Stone(Board.Color.BLACK));
        }
        /* End init */

        /* Homes */
        pips.put(Pip.BLACK_HOME, new Pip(Pip.BLACK_HOME));
        pips.put(Pip.WHITE_HOME, new Pip(Pip.WHITE_HOME));
        /* End init */

        /* Bar */
        pips.put(Pip.BLACK_BAR, new Pip(Pip.BLACK_BAR));
        pips.put(Pip.WHITE_BAR, new Pip(Pip.WHITE_BAR));
        /* End init */

        // Initialise list of possible moves
        clearPossibleMoves();
    }

    /**
     * Clears the list of possible moves.
     */
    public void clearPossibleMoves() {
        for (int i = Pip.BLACK_BAR; i <= Pip.WHITE_BAR; i++) {
            possibleMoves.put(pips.get(i), new HashSet<>());
        }
    }

    /**
     * Checks if a hitbox is associated with a pip.
     *
     * @param hitbox
     * @param isPossibleEnd
     * @return
     */
    public Pip getAssociatedPip(Hitbox hitbox, boolean isPossibleEnd) {
        if (hitbox == null)
            return null;

        for (Pip p : (isPossibleEnd ? possibleMoves.keySet() : pips.values())) {
            if (p.getHitbox() == hitbox) {
                return p;
            }
        }

        return null;
    }

    /**
     * Checks if there are any possible moves in the current set of pips.
     *
     * @return true if there are possible moves, false otherwise
     */
    public boolean hasPossibleMoves() {
        for (HashSet<Move> m : possibleMoves.values())
            if (!m.isEmpty())
                return true;

        return false;
    }

    /**
     * Gets the set of possible moves which end at the specified pip.
     *
     * @param pip ending pip
     * @return the set of possible moves (empty set if no moves)
     */
    public HashSet<Move> getPossibleMovesTo(Pip pip) {
        return possibleMoves.get(pip);
    }

    /**
     * Highlights a move. Un-highlights all non-move pips.
     *
     * @param move the move
     */
    public void highlightMove(Move move) {
        // Clear all
        highlightNothing();

        // Store some numbers
        int start = move.getStartPip(), end = move.getEndPip();

        // Highlight starting pip
        pips.get(start).setHighlightStart(true);

        // Highlight intermediate pips, if any
        int direction = end - start > 0 ? 1 : -1;
        int bar = direction == 1 ? Pip.BLACK_BAR : Pip.WHITE_BAR;
        int home = direction == 1 ? Pip.BLACK_HOME : Pip.WHITE_HOME;

        if (start == bar) start += direction;

        for (int i = 0; i < move.getDiceToUse().size() - 1; i++) {
            start += move.getDiceToUse().get(i).getValue() * direction;
            if (start * direction >= home * direction) break; // If move takes the stone home, don't bother drawing it as an intermediate pip
            pips.get(start).setHighlightIntermediate(true);
        }

        // Highlight ending pip
        pips.get(end).setHighlightEnd(true);
    }

    /**
     * Highlights all pips which can be moved to. Un-highlights all pips which cannot be moved to.
     */
    public void highlightPossibleEnds() {
        for (Pip end : possibleMoves.keySet()) {
            end.setHighlightStart(false);
            end.setHighlightIntermediate(false);
            end.setHighlightEnd(!possibleMoves.get(end).isEmpty());
        }
    }

    /**
     * Highlights no pips.
     */
    public void highlightNothing() {
        for (Pip pip : possibleMoves.keySet()) {
            pip.setHighlightStart(false);
            pip.setHighlightIntermediate(false);
            pip.setHighlightEnd(false);
        }
    }

    /**
     * Calculates all possible moves for the specified player given the combinations of dice available to the player.
     *
     * @param diceCombos combinations of dice available for play
     * @param color colour of the player in question
     */
    public void calculatePossibleMoves(ArrayList<ArrayList<Die>> diceCombos, Board.Color color) {
        // Reset list of possible moves
        clearPossibleMoves();

        // Useful numbers
        int bar = (color == Board.Color.BLACK) ? Pip.BLACK_BAR : Pip.WHITE_BAR;
        int home = (color == Board.Color.BLACK) ? Pip.BLACK_HOME : Pip.WHITE_HOME;

        /* Iterate over all dice combos (generated from Dice) */
        for (ArrayList<Die> diceCombo : diceCombos) {

            ArrayList<ArrayList<Die>> permutations = getPermutationsOfDice(diceCombo);

            /* Iterate over all permutations of current dice combination */
            for (ArrayList<Die> permutation : permutations) {

                // Fields describing potential move
                int startPos, endPos, pos;
                boolean canCompleteMove, canMoveHome = false;

                // Set values
                int first, last, d; // d is direction of travel over pips (+/- 1)
                if (color.equals(Board.Color.BLACK)) {
                    first = 1;
                    last = 24;
                    d = 1;
                } else {
                    first = 24;
                    last = 1;
                    d = -1;
                }

                /* Determine possible starting points */

                // Stones on bar have top priority (must be moved into play first)
                if (pips.get(bar).isPossibleStart(color)) {
                    startPos = bar;
                    endPos = bar;
                }

                // Else, check if stones can bear off (move home)
                else {
                    canMoveHome = true;

                    for (int i = last - 6 * d; i * d >= first * d; i -= d) {
                        if (pips.get(i).isPossibleStart(color)) {
                            canMoveHome = false;
                            break;
                        }
                    }

                    startPos = canMoveHome ? last - 6 * d : first;
                    endPos = last;
                }

                /* Iterate over all possible starting positions */
                for (int p = startPos; p * d <= endPos * d; p += d) { // p is the starting position of the move

                    // Continue if pip is not a valid start
                    if (!pips.get(p).isPossibleStart(color)) {
                        continue;
                    }

                    canCompleteMove = true;

                    // Set position pointer
                    // If stone is starting from the bar, shift the position pointer forward 1 pip (bars are 2 less than the first pip)
                    pos = p + (p == bar ? d : 0);

                    // Create an output list to eliminate unused dice
                    ArrayList<Die> diceOut = new ArrayList<>();

                    // Check if whole permutation can successfully be played from the current starting point
                    for (Die die : permutation) {
                        pos += d * die.getValue();

                        // If move takes the player past the home, set the position tracker to the home (allow overshooting)
                        if (pos * d > home * d) pos = home;

                        // Invalid move case 1: move would take player into the home but player cannot move home
                        if (pos == home && !canMoveHome) {
                            canCompleteMove = false;
                            break;
                        }

                        // Invalid move case 2: end pip does not allow for move (see Pip.isPossibleEnd(Board.Color color))
                        if (!pips.get(pos).isPossibleEnd(color)) {
                            canCompleteMove = false;
                            break;
                        }

                        diceOut.add(die);
                    }

                    // Add move to list of possible moves if whole permutation can be played
                    if (canCompleteMove) {
                        possibleMoves.get(pips.get(pos)).add(new Move(p, pos, diceOut));
                    }

                } /* End starting positions loop */

            } /* End specific permutation loop */

        } /* End combination loop */
    }

    /**
     * Gets all the permutations of the current dice combination.
     *
     * @param dice the dice combination
     * @return all permutations
     */
    private ArrayList<ArrayList<Die>> getPermutationsOfDice(ArrayList<Die> dice) {

        // For multiple dice, check if all dice are of the same value
        boolean sameValue = true;

        if (dice.size() >= 2) {
            for (int i = 1; i < dice.size(); i++) {
                if (!dice.get(i - 1).equals(dice.get(i))) {
                    sameValue = false;
                    break;
                }
            }
        }

        // Initialise output
        ArrayList<ArrayList<Die>> output = new ArrayList<>();

        // Create output
        if (sameValue) { // Double rolls
            // Only one permutation to add
            output.add(new ArrayList<>(dice));
        } else { // Dice must be unique
            permuteDiceHelper(dice, 0, output);
        }

        return output;
    }

    /**
     * Recursively puts all permutations of a dice list into an output list.
     * Method should only be called from inside getPermutationsOfDice().
     *
     * @param dice list of dice to permute
     * @param k beginning index of permutation
     * @param output output list
     */
    private void permuteDiceHelper(ArrayList<Die> dice, int k, ArrayList<ArrayList<Die>> output) {
        // Base case: if starting at the end of the list, output the list
        if (k == dice.size() - 1){
            output.add(new ArrayList<>(dice));
        }

        // Swap first element with any other element and permute the rest of the list
        for (int i = k; i < dice.size(); i++) {
            java.util.Collections.swap(dice, i, k);
            permuteDiceHelper(dice, k + 1, output);
            java.util.Collections.swap(dice, k, i);
        }

    }

    /**
     * Draws all pips of this object.
     *
     * @param g the graphics to use
     */
    public void draw(Graphics g) {
        for (Integer key : pips.keySet()) {
            pips.get(key).draw(g);
        }
    }

    /**
     * Moves a stone from one pip to another.
     * NOTE: this method does not remove the dice associated with the move from any lists of dice.
     *
     * @param move the move to execute
     */
    public void executeMove(Move move) {
        // Do nothing if there is no move
        if (move == null)
            return;

        // Execute move die by die
        int direction = move.getEndPip() - move.getStartPip() > 0 ? 1 : -1;

        // Store some values
        int start = move.getStartPip(), end;
        int bar = direction == 1 ? Pip.BLACK_BAR : Pip.WHITE_BAR;
        int home = direction == 1 ? Pip.BLACK_HOME : Pip.WHITE_HOME;

        for (Die die : move.getDiceToUse()) {
            // Update end position of move
            end = start + die.getValue() * direction;
            if (start == bar) end += direction; // Adjust move distance if starting from the bar
            if (end * direction > home * direction) end = home; // Check for overshooting

            // Knock stone to bar if the end pip stone color does not match the start pip stone color
            if ((pips.get(end).getColor() != Board.Color.NONE) &&
                    (pips.get(end).getColor() != pips.get(start).getColor()))
            {
                int destBar = pips.get(end).getColor() == Board.Color.BLACK ? Pip.BLACK_BAR : Pip.WHITE_BAR;
                pips.get(destBar).addStone(pips.get(end).popStone());
            }

            // Move the stone
            pips.get(end).addStone(pips.get(start).popStone());

            // Prepare next iteration
            start = end;
        }

    }

    /**
     * Returns the list of pips as an ArrayList.
     *
     * @return list of pips
     */
    public ArrayList<Pip> getAsArrayList() {
        ArrayList<Pip> out = new ArrayList<>();

        for (Integer key : pips.keySet()) {
            out.add(pips.get(key));
        }

        return out;
    }

    /**
     * Enables or disables all hitboxes.
     *
     * @param set state to set
     */
    public void setAllHitboxes(boolean set) {
        for (Pip p : pips.values()) {
            p.getHitbox().setHoverable(set);
            p.getHitbox().setClickable(set);
        }
    }

    /**
     * Checks for the winner of the game.
     *
     * @return color corresponding to the winner of the game (Board.Color.NONE if no winner yet)
     */
    public Board.Color checkWinner(){
        if (pips.get(Pip.BLACK_HOME).getStoneSize() == 15)
            return Board.Color.BLACK;
        if (pips.get(Pip.WHITE_HOME).getStoneSize() == 15)
            return Board.Color.WHITE;
        return Board.Color.NONE;
    }

}
