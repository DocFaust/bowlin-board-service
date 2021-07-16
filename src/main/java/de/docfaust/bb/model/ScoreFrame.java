package de.docfaust.bb.model;

import java.util.ArrayList;
import java.util.List;

public class ScoreFrame {

	private int number;
	private List<String> rolls = new ArrayList<>();
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
