package de.docfaust.bb.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.docfaust.bb.model.FinalRound;
import de.docfaust.bb.model.NormalRound;
import de.docfaust.bb.model.Round;
import de.docfaust.bb.model.ScoreFrame;
import de.docfaust.bb.model.exception.InvalidRollException;

@Service
public class RoundServiceImpl implements RoundService {
	private static final String SPARE_SYMBOL = "/";
	private static final String STRIKE_SYMBOL = "X";
	private List<Round> rounds = new ArrayList<>(10);
	private int roundindex = -1;

	public RoundServiceImpl() {
		initializeRounds();
	}

	@Override
	public void initializeRounds() {
		rounds.clear();
		roundindex = -1;
		for (int i = 0; i < 9; i++) {
			rounds.add(new NormalRound(i));
		}
		rounds.add(new FinalRound(9));
		roundindex++;
	}

	@Override
	public void addRoll(int score) throws InvalidRollException {
		Round current = rounds.get(roundindex);

		current.addRoll(score);

		if (!current.isRollAvailable()) {
			roundindex++;
		}
	}

	@Override
	public List<ScoreFrame> getScoreBoard() {
		List<ScoreFrame> frames = new ArrayList<>(10);
		for (int i = 0; i <= 9; i++) {
			frames.add(new ScoreFrame(i+1));
		}

		int totalScore = 0;
		// We have to iterate frames with an index
		for (int i = 0; i < rounds.size(); i++) {
			ScoreFrame frame = frames.get(i);
			Round currentRound = rounds.get(i);
			if (!currentRound.isEmpty()) {
				int currentScore = 0;
				if (currentRound instanceof NormalRound) {
					// Add next two rolls in case of Strike if available
					if (currentRound.isStrike()) {
						frame.getRolls().add(STRIKE_SYMBOL);
						frame.getRolls().add("");

						Round nextRound = rounds.get(i + 1);
						currentScore = 10 + nextRound.getScore();

					} else if (currentRound.isSpare()) {

						frame.getRolls().add(String.valueOf(currentRound.getRoll(0)));
						frame.getRolls().add(SPARE_SYMBOL);

						Round nextRound = rounds.get(i + 1);

						int firstRoll = nextRound.getRoll(0);
						currentScore = 10 + ((firstRoll == -1) ? 0 : firstRoll);
//						totalScore += currentScore;

					} else {
						List<String> scoresAsList = Arrays.stream(currentRound.getRolls())
								.mapToObj(roll -> roll == -1 ? "" : String.valueOf(roll)).collect(Collectors.toList());
						frame.setRolls(scoresAsList);
						currentScore = currentRound.getScore();
					}
				} else {
					// Final Round
					frame.setNumber(10);
					int[] rolls = currentRound.getRolls();
					for (int j : rolls) {
						if (j != -1) {
							frame.getRolls().add((j == 10) ? "X" : String.valueOf(j));
						} else {
							frame.getRolls().add("");
						}
					}
					currentScore = currentRound.getScore();
				}

				totalScore += currentScore;
				frame.setTotalScore(totalScore);
			}
		}

		return frames;
	}
}
