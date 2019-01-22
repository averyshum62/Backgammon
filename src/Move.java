import java.io.Serializable;
import java.util.ArrayList;

public class Move implements Serializable {
	private int startPip, endPip;
	private ArrayList<Die> diceToUse;
	
	public Move(int start, int end, ArrayList<Die> diceToUse) {
		startPip = start;
		endPip = end;
		this.diceToUse = diceToUse;
	}

	public int getStartPip() {
	    return startPip;
    }

    public int getEndPip() {
	    return endPip;
    }

    public ArrayList<Die> getDiceToUse() {
	    return diceToUse;
    }
}
