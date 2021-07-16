package de.docfaust.bb.service;

import java.util.List;

import de.docfaust.bb.model.ScoreFrame;
import de.docfaust.bb.model.exception.InvalidRollException;

/**
 * RoundService providing all necessary Bowling Functionality.
 * @author Werner Faust
 *
 */
public interface RoundService {

	/**
	 * Builds and returns the scoreboard.
	 * Magic spaghetti built of nerves and facepalms 
	 * @return the Scoreboard calculated and prepared for UI.
	 */
	List<ScoreFrame> getScoreBoard();
	
	/**
	 * Adds a roll to the board.
	 * @param score Score of the roll.
	 * @throws InvalidRollException Thrown if an invalid amount of pins was rolled. 
	 */
	void addRoll(int score) throws InvalidRollException;

	/**
	 * Resets the Scoreboard to let the games begin anew.
	 */
	void initializeRounds();

	/**
	 * Returns true if a game is in Progress
	 * @return true if a game is in Progress
	 */
	boolean isGameInProgress();

}