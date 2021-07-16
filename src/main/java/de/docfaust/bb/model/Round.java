package de.docfaust.bb.model;

import de.docfaust.bb.model.exception.InvalidRollException;

public interface Round {

	void addRoll(int pins) throws InvalidRollException;

	int getRoll(int i);
	int [] getRolls();
	int getScore();

	boolean isStrike();

	boolean isSpare();

	boolean isRollAvailable();

	int getRoundIndex();

	boolean isEmpty();

}