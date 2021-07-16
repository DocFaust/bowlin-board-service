package de.docfaust.bb.model;

import de.docfaust.bb.model.exception.InvalidRollException;

/**
 * The first 9 rounds are normal having 2 rolls.
 * @author Werner Faust
 *
 */
public class NormalRound extends AbstractRound {
	int[] rolls = new int[] { -1, -1 };

	/**
	 * Initializes the Round setting the Roundnumber.
	 * @param roundNumber
	 */
	public NormalRound(int roundNumber) {
		super(roundNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isStrike() {
		return rolls[0] == 10;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSpare() {
		return rolls[0] + rolls[1] == 10;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int[] getRolls() {
		return this.rolls;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRollAvailable() {
		// In a Normal Round it's finished after a Strike 
		return super.isRollAvailable() && !isStrike();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	void specialCheck(int pins) throws InvalidRollException {
		// Number of Rolls not more than 10
		if(rolls[0] + pins > 10) {
			throw new InvalidRollException("Number of Pins bigger than 10");
		}
	}
}
