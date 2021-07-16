package de.docfaust.bb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.docfaust.bb.model.ScoreFrame;
import de.docfaust.bb.model.exception.InvalidRollException;
public class RoundServiceTest {
	
	@Test
	void testGetScoreBoard() {
		RoundService service = new RoundServiceImpl();
		try {
			service.addRoll(8);
			List<ScoreFrame> scoreBoard = service.getScoreBoard();
			assertThat(scoreBoard).isNotNull().hasSize(10);
			ScoreFrame frame = scoreBoard.get(0);
			assertThat(frame.getNumber()).isEqualTo(1);
			assertThat(frame.getRolls()).isNotNull().hasSize(2).contains("8", "");
			
			assertThat(frame.getTotalScore()).isEqualTo(8);
		} catch (InvalidRollException e) {
			fail("not expected");
			e.printStackTrace();
		}
	}
	
	@Test
	void testThePerfectGame() {
		RoundService service = new RoundServiceImpl();
		try {
			// F1
			service.addRoll(10);

			int index = 0;
			int totalScore = 10;
			String[] rolls = {"", "X"};
			
			checkBoard(service, index, totalScore, rolls);
			
			//F2
			service.addRoll(10);
			checkBoard(service, 1, 30, "", "X");
			checkBoard(service, 0, 20, "", "X");

			//F3
			service.addRoll(10);
			checkBoard(service, 2, 60, "", "X");
			checkBoard(service, 1, 50, "", "X");
			checkBoard(service, 0, 30, "", "X");
			
			//F4
			service.addRoll(10);
			checkBoard(service, 3, 90, "", "X");
			checkBoard(service, 2, 80, "", "X");
			checkBoard(service, 1, 60, "", "X");
		
			//F5
			service.addRoll(10);
			checkBoard(service, 4, 120, "", "X");
			checkBoard(service, 3, 110, "", "X");
			checkBoard(service, 2, 90, "", "X");
		
			//F6
			service.addRoll(10);
			checkBoard(service, 5, 150, "", "X");
			checkBoard(service, 4, 140, "", "X");
			checkBoard(service, 3, 120, "", "X");
		
			//F7
			service.addRoll(10);
			checkBoard(service, 6, 180, "", "X");
			checkBoard(service, 5, 170, "", "X");
			checkBoard(service, 4, 150, "", "X");
		
			//F8
			service.addRoll(10);
			checkBoard(service, 7, 210, "", "X");
			checkBoard(service, 6, 200, "", "X");
			checkBoard(service, 5, 180, "", "X");
		
			//F9
			service.addRoll(10);
			checkBoard(service, 8, 240, "", "X");
			checkBoard(service, 7, 230, "", "X");
			checkBoard(service, 6, 210, "", "X");
			
			//F10-1
			service.addRoll(10);
			checkBoard(service, 9, 270, "X", "", "");
			checkBoard(service, 8, 260, "", "X");
			checkBoard(service, 7, 240, "", "X");

			// F10-2
			service.addRoll(10);
			checkBoard(service, 9, 290, "X", "X", "");
			checkBoard(service, 8, 270, "", "X");
			checkBoard(service, 7, 240, "", "X");
			
			// F10-3
			service.addRoll(10);
			checkBoard(service, 9, 300, "X", "X", "X");
			checkBoard(service, 8, 270, "", "X");
			checkBoard(service, 7, 240, "", "X");
			
		} catch (InvalidRollException e) {
			fail("not expected");
			e.printStackTrace();
		}
	}

	private void checkBoard(RoundService service, int index, int totalScore, String... rolls) {
		List<ScoreFrame> scoreBoard = service.getScoreBoard();
		printScoreBoard(scoreBoard);
		assertThat(scoreBoard).isNotNull().hasSize(10);
		ScoreFrame currentFrame = scoreBoard.get(index);
		assertThat(currentFrame.getNumber()).isEqualTo(index + 1);
		assertThat(currentFrame.getRolls()).isNotNull().hasSize(rolls.length).contains(rolls);
		assertThat(currentFrame.getTotalScore()).isEqualTo(totalScore);
	}
	
	private void printScoreBoard(List<ScoreFrame> frames) {
		System.out.println("--- Scoreboard ---");
		for (ScoreFrame frame : frames) {
			System.out.println(frame.toString());
		}
		System.out.println("--- Scoreboard ---");
	}

}
