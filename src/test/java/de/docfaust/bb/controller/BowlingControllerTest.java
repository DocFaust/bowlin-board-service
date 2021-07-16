package de.docfaust.bb.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import de.docfaust.bb.controller.exception.HTTPInvalidScoreException;
import de.docfaust.bb.model.ScoreFrame;

@SpringBootTest
public class BowlingControllerTest {

	@Autowired
	private BowlingController controller;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

	/**
	 * All in one test to ensure the order.
	 */
	@Test
	public void testAddScore() {
		// F1
		// Number
		addScore(4);
		checkBoard(0, 4, "4");
		addScore(4);
		checkBoard(0, 8, "4", "4");

		// F2
		// Strike
		addScore(10);
		checkBoard(1, 18, "X", "");

		// F3 Next 2
		addScore(4);
		checkBoard(1, 22, "X", "");
		checkBoard(2, 26, "4", "");
		addScore(4);
		checkBoard(1, 26, "X", "");
		checkBoard(2, 34, "3", "");

		// F4
		// Spare
		addScore(4);
		checkBoard(3, 38, "4", "");
		addScore(6);
		checkBoard(3, 44, "4", "/");

		// F5 Next
		addScore(7);
		checkBoard(3, 51, "4", "/");
		checkBoard(4, 58, "7", "");

		addScore(2);
		checkBoard(3, 51, "4", "/");
		checkBoard(4, 60, "7", "4");

		// F6 1 Miss
		addScore(0);
		checkBoard(5, 60, "", "");
		addScore(2);
		checkBoard(5, 62, "", "");
		
		// F7 2 Miss
		addScore(2);
		checkBoard(6, 64, "2", "");
		addScore(0);
		checkBoard(6, 64, "2", "");
		
		// F8
		try {
			Model m = new BindingAwareModelMap();
			controller.addRoll(11, m);
			assertThat(m.getAttribute("message")).isNotNull().isEqualTo("Pins out of range: 11");
			checkBoard(7, 0, "", "");
		} catch (HTTPInvalidScoreException e) {
			e.printStackTrace();
		}

		addScore(6); // Counts
		checkBoard(7, 70, "6", "");
		
		try {
			Model m = new BindingAwareModelMap();
			controller.addRoll(6, m);
			assertThat(m.getAttribute("message")).isNotNull().isEqualTo("Number of Pins bigger than 10");
			checkBoard(7, 70, "6", "");
		} catch (HTTPInvalidScoreException e) {
			e.printStackTrace();
		}
		addScore(3); // Counts
		checkBoard(7, 73, "6", "");
		
		//F9
		addScore(3); // Counts
		addScore(4); // Counts
		checkBoard(8, 80, "3", "4");
		
		//F10 Final
		addScore(10); // Counts
		checkBoard(9, 90, "X", "", "");
		addScore(5); // Counts
		checkBoard(9, 95, "X", "5", "");
		addScore(4); // Counts
		checkBoard(9, 99, "X", "5", "4");


	}

	private void checkBoard(int frameNumber, int totalScore, String... roll) {
		Model m =new BindingAwareModelMap();
		String ret = controller.getScoreBoard(m);
		List<ScoreFrame> frames = (List<ScoreFrame>) m.getAttribute("scoreboard");
		printScoreBoard(frames);
		ScoreFrame frame =frames.get(frameNumber);
		assertThat(frame.getTotalScore()).isEqualTo(totalScore);

		List<String> rolls = frame.getRolls();
		for (int i = 0; i > rolls.size(); i++) {
			String froll = rolls.get(i);
			String croll = roll[i];
			assertThat(froll).isEqualTo(croll);
		}

	}

	private void printScoreBoard(List<ScoreFrame> frames) {
		System.out.println("--- Scoreboard ---");
		for (ScoreFrame frame : frames) {
			System.out.println(frame.toString());
		}
		System.out.println("--- Scoreboard ---");
	}

	private void addScore(int score) {
		try {
			controller.addRoll(score, new BindingAwareModelMap());
		} catch (HTTPInvalidScoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
