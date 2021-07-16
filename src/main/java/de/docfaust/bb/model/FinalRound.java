package de.docfaust.bb.model;

import de.docfaust.bb.model.exception.InvalidRollException;

/**
 * Special last Round, having 3 Rolls.
 * @author wfa339
 *
 */
public class FinalRound extends AbstractRound {
	int[] rolls = new int[] { -1, -1, -1 };

	/**
	 * Initializes the Final Round setting the index.
	 * @param roundNumber
	 */
	public FinalRound(int roundNumber) {
		super(roundNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isStrike() {
		// More than one Strike in FinalRound
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSpare() {
		// No Spares in FinalRound
		return false;
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
	void specialCheck(int pins) throws InvalidRollException {
		// wierd number of combinations I don't want to consider in an example
		// In the real world you would not be able to hit pore pins than standing ;-)
	}
}
