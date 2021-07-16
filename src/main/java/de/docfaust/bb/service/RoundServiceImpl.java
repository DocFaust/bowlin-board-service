package de.docfaust.bb.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import de.docfaust.bb.model.FinalRound;
import de.docfaust.bb.model.NormalRound;
import de.docfaust.bb.model.Round;
import de.docfaust.bb.model.ScoreFrame;
import de.docfaust.bb.model.exception.InvalidRollException;

/**
 * Implementation of the Round Service.
 * 
 * @author Werner Faust
 *
 */
@SessionScope
@Service
public class RoundServiceImpl implements RoundService {

	private static final String SPARE_SYMBOL = "/";
	private static final String STRIKE_SYMBOL = "X";

	/**
	 * The ScoreBoard.
	 */
	private List<Round> rounds = new ArrayList<>(10);

	/**
	 * Index, where w currently are.
	 */
	private int roundindex = -1;

	/**
	 * From the very beginning.
	 */
	public RoundServiceImpl() {
		initializeRounds();
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isGameInProgress() {
		return roundindex < 10;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addRoll(int score) throws InvalidRollException {
		if (roundindex >= 10) {
			throw new InvalidRollException("Game ended");
		}
		Round current = rounds.get(roundindex);

		current.addRoll(score);

		if (!current.isRollAvailable()) {
			roundindex++;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ScoreFrame> getScoreBoard() {
		// Build UI Frames
		List<ScoreFrame> frames = new ArrayList<>(10);
		for (int i = 0; i <= 9; i++) {
			frames.add(new ScoreFrame(i + 1));
		}

		int totalScore = 0;

		// We have to iterate frames with an index
		for (int i = 0; i < rounds.size(); i++) {
			ScoreFrame frame = frames.get(i);
			Round currentRound = rounds.get(i);
			if (!currentRound.isEmpty()) {
				int currentScore = 0;

				// First 9 rounds
				if (currentRound instanceof NormalRound) {
					// Add next two rolls in case of Strike if available
					if (currentRound.isStrike()) {
						frame.getRolls().add(STRIKE_SYMBOL);
						frame.getRolls().add("");

						Round nextRound = rounds.get(i + 1);
						// 10 for this Strike
						currentScore += 10;

						// Another strike
						if (nextRound.getRoll(0) == 10) {
							// Next one if Final
							if (nextRound instanceof FinalRound) {
								int next = nextRound.getRoll(0);
								currentScore += next != -1 ? next : 0;
								next = nextRound.getRoll(1);
								currentScore += next != -1 ? next : 0;
							} else {
								// Next one is normal
								// 10 for next strike
								currentScore += 10;
								Round nextNextRound = rounds.get(i + 2);
								int next = nextNextRound.getRoll(0);
								// whatever is in round after next round
								currentScore += next != -1 ? next : 0;
							}
						} else {
							// Just normal Round
							int r1 = nextRound.getRoll(0);
							int r2 = nextRound.getRoll(1);
							currentScore += (r1 == -1 ? 0 : r1) + (r2 == -1 ? 0 : r2);
						}

					} else if (currentRound.isSpare()) {

						frame.getRolls().add(String.valueOf(currentRound.getRoll(0)));
						frame.getRolls().add(SPARE_SYMBOL);

						Round nextRound = rounds.get(i + 1);

						int firstRoll = nextRound.getRoll(0);
						currentScore = 10 + ((firstRoll == -1) ? 0 : firstRoll);

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
					currentScore += currentRound.getScore();
				}

				totalScore += currentScore;
				frame.setTotalScore(totalScore);
			}
		}

		return frames;
	}
}
