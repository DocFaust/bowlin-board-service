package de.docfaust.bb.model;

import de.docfaust.bb.model.exception.InvalidRollException;

public class NormalRound extends AbstractRound {
	int[] rolls = new int[] { -1, -1 };

	public NormalRound(int roundNumber) {
		super(roundNumber);
	}

	@Override
	public boolean isStrike() {
		return rolls[0] == 10;
	}

	@Override
	public boolean isSpare() {
		return rolls[0] + rolls[1] == 10;
	}

	@Override
	public int[] getRolls() {
		return this.rolls;
	}
	
	@Override
	public boolean isRollAvailable() {
		// In a Normal Round it's finished after a Strike 
		return super.isRollAvailable() && !isStrike();
	}

	@Override
	void specialCheck(int pins) throws InvalidRollException {
		// Number of Rolls not more than 10
		if(rolls[0] + pins > 10) {
			throw new InvalidRollException("Number of Pins bigger than 10");
		}
		
	}
}
