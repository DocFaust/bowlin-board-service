package de.docfaust.bb.model;

import java.util.Arrays;

import de.docfaust.bb.model.exception.InvalidRollException;


/**
 * Providing all functionality the rounds have in common. 
 * @author Werner Faust
 *
 */
public abstract class AbstractRound implements Round {

	/**
	 * index of the round.
	 */
	protected int roundIndex;
	
	/**
	 * Number of Pins.
	 */
	protected static final int MAX_PINS = 10;
	
	/**
	 * Performs the special checks the rounds have to do. 
	 * @param pins number of rolled pins.
	 * @throws InvalidRollException is thrown if the roll was invalid.
	 */
	abstract void specialCheck(int pins) throws InvalidRollException;

	/**
	 * Guess what ;-)
	 * @param roundNumber index
	 */
	public AbstractRound(int roundNumber) {
		roundIndex = roundNumber;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRoll(int i) {
		return getRolls()[i];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRoundIndex() {
		return roundIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getScore() {
		return Arrays.stream(getRolls()).filter(roll -> roll > -1).sum();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRollAvailable() {
		// Counting empty rolls
		long size = Arrays.stream(getRolls()).filter(roll -> roll == -1).count();
		return size > 0;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addRoll(int pins) throws InvalidRollException {
		checkRoll(pins);

		boolean rollSet = false;
		for (int i = 0; i < getRolls().length && !rollSet; i++) {
			if (getRoll(i) == -1) {
				getRolls()[i] = pins;
				rollSet = true;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		long size = Arrays.stream(getRolls()).filter(roll -> roll == -1).count();
		return size == getRolls().length;
	}
	
	/**
	 * Performing the check if the Roll was valid. IN the real world you simply can't roll more pins than available. 
	 * @param pins rolled pins
	 * @throws InvalidRollException thrown if invalid roll
	 */
	protected void checkRoll(int pins) throws InvalidRollException {
		specialCheck(pins);
		if (pins > MAX_PINS || pins < 0) {
			throw new InvalidRollException("Pins out of range: " + pins);
		}
		
		if (!isRollAvailable()) {
			throw new InvalidRollException("Limit of rolls exceeded");
		}
	}
}