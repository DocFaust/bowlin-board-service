package de.docfaust.bb.model;

import de.docfaust.bb.model.exception.InvalidRollException;

public class FinalRound extends AbstractRound {
	int[] rolls = new int[] { -1, -1, -1 };

	public FinalRound(int roundNumber) {
		super(roundNumber);
	}

	@Override
	public boolean isStrike() {
		// More than one Strike in FinalRound
		return false;
	}

	@Override
	public boolean isSpare() {
		// No Spares in FinalRound
		return false;
	}

	@Override
	public int[] getRolls() {
		return this.rolls;
	}

	@Override
	void specialCheck(int pins) throws InvalidRollException {
		// wierd number of combinations I don't want to consider in an example
		// In the real world you would not be able to hit pore pins than standing ;-)
	}
}
