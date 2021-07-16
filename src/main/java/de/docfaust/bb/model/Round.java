package de.docfaust.bb.model;

import de.docfaust.bb.model.exception.InvalidRollException;

/**
 * A Round of Bowling. Normally 2 Rolls except the last round having 3
 * @author Werner Faust
 *
 */
public interface Round {

	/**
	 * Adds the score of a roll to the round
	 * @param pins Number of fallen Pins.
	 * @throws InvalidRollException thrown in case of impossible entries.
	 */
	void addRoll(int pins) throws InvalidRollException;

	/**
	 * Returns the score of a given roll.
	 * @param i INdex of the roll;
	 * @return the score or -1 if not set
	 */
	int getRoll(int i);
	
	
	/**
	 * returns all rolls as intArray
	 * @return rolls;
	 */
	int[] getRolls();
	
	/**
	 * returns the cummulated Score.
	 * @return score
	 */
	int getScore();

	/**
	 * returns if round is a Strike.
	 * @return true if Strike
	 */
	boolean isStrike();

	/**
	 * returns if round is a Spare.
	 * @return true if Spare
	 */
	boolean isSpare();

	/**
	 * Returns if there are aditional rolls possible.
	 * @return true if roll is possible.
	 */
	boolean isRollAvailable();

	/**
	 * returns the index of the round
	 * @return Idex 0 to 9;
	 */
	int getRoundIndex();

	/**
	 * returns if the Round has not been rolled yet.
	 * @return true if all rolls are -1
	 */
	boolean isEmpty();

}