package de.docfaust.bb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO Class for the UI
 * @author Werner Faust
 *
 */
public class ScoreFrame {

	/**
	 * Number of the Frame from 1 to 10;
	 */
	private int number;
	
	/**
	 * Array of Rolls. 
	 */
	private List<String> rolls = new ArrayList<>();
	
	/**
	 * Total score.
	 */
	private int totalScore;

	public ScoreFrame(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<String> getRolls() {
		return rolls;
	}

	public void setRolls(List<String> rolls) {
		this.rolls = rolls;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	/**
	 * Logging purposes.
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder("ScoreFrame: [");
		b.append("Number: ").append(number).append(", ");
		b.append("Rolls: [");
		for (String roll : rolls) {
			b.append("Roll:").append(roll).append("\t");
		}
		b.append("] ");
		b.append("totalScore: ").append(totalScore);
		b.append("]");
		return b.toString();
	}
}
