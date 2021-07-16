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
}
