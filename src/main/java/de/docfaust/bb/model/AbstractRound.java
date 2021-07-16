package de.docfaust.bb.model;

import java.util.Arrays;

import de.docfaust.bb.model.exception.InvalidRollException;

public abstract class AbstractRound implements Round {

	protected int roundIndex;
	protected static final int MAX_PINS = 10;
	abstract void specialCheck(int pins) throws InvalidRollException;

	public AbstractRound(int roundNumber) {
		roundIndex = roundNumber;
	}

	@Override
	public int getRoll(int i) {
		return getRolls()[i];
	}

	@Override
	public int getRoundIndex() {
		return roundIndex;
	}

	@Override
	public int getScore() {
		return Arrays.stream(getRolls()).filter(roll -> roll > -1).sum();
	}

	@Override
	public boolean isRollAvailable() {
		// Counting empty rolls
		long size = Arrays.stream(getRolls()).filter(roll -> roll == -1).count();
		return size > 0;
	}

	protected void checkGeneral(int pins) throws InvalidRollException {
		specialCheck(pins);
		if (pins > MAX_PINS || pins < 0) {
			throw new InvalidRollException("Pins out of range: " + pins);
		}

		if (!isRollAvailable()) {
			throw new InvalidRollException("Limit of rolls exceeded");
		}
	}

	@Override
	public void addRoll(int pins) throws InvalidRollException {
		checkGeneral(pins);

		boolean rollSet = false;
		for (int i = 0; i < getRolls().length && !rollSet; i++) {
			if (getRoll(i) == -1) {
				getRolls()[i] = pins;
				rollSet = true;
			}
		}
	}

	@Override
	public boolean isEmpty() {
		long size = Arrays.stream(getRolls()).filter(roll -> roll == -1).count();
		return size == getRolls().length;
	}
}