package de.docfaust.bb.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import de.docfaust.bb.model.exception.InvalidRollException;

class RoundTest {

	@Test
	void testAddInvalidRollBigger() {
		Round round = new NormalRound(0);
		try {
			round.addRoll(11);
			fail("Not expected");
		} catch (InvalidRollException e) {
			e.printStackTrace();
			assertThat(round.getRoundIndex()).isEqualTo(0);
			assertThat(round.getRoll(0)).isEqualTo(-1);
			assertThat(round.getRoll(1)).isEqualTo(-1);
			assertThat(round.getScore()).isEqualTo(0);
			assertThat(round.isRollAvailable()).isTrue();
			assertThat(round.isStrike()).isFalse();
			assertThat(round.isSpare()).isFalse();
		}
	}

	@Test
	void testAdd1Roll() {
		Round round = new NormalRound(0);
		try {
			round.addRoll(4);
			assertThat(round.getRoundIndex()).isEqualTo(0);
			assertThat(round.getRoll(0)).isEqualTo(4);
			assertThat(round.getRoll(1)).isEqualTo(-1);
			assertThat(round.getScore()).isEqualTo(4);
			assertThat(round.isRollAvailable()).isTrue();
			assertThat(round.isStrike()).isFalse();
			assertThat(round.isSpare()).isFalse();
		} catch (InvalidRollException e) {
			fail("Not expected");
		}
	}

	@Test
	void testAdd2Roll() {
		Round round = new NormalRound(0);
		try {
			round.addRoll(4);
			round.addRoll(4);
			assertThat(round.getRoundIndex()).isEqualTo(0);
			assertThat(round.getRoll(0)).isEqualTo(4);
			assertThat(round.getRoll(1)).isEqualTo(4);
			assertThat(round.getScore()).isEqualTo(8);
			assertThat(round.isRollAvailable()).isFalse();
			assertThat(round.isStrike()).isFalse();
			assertThat(round.isSpare()).isFalse();
		} catch (InvalidRollException e) {
			fail("Not expected");
		}
	}

	@Test
	void testAdd3Roll() {
		Round round = new NormalRound(0);
		try {
			round.addRoll(4);
			round.addRoll(4);
			round.addRoll(4);
			fail("Don't do this");
		} catch (InvalidRollException e) {
			assertThat(round.getRoundIndex()).isEqualTo(0);
			assertThat(round.getRoll(0)).isEqualTo(4);
			assertThat(round.getRoll(1)).isEqualTo(4);
			assertThat(round.getScore()).isEqualTo(8);
			assertThat(round.isRollAvailable()).isFalse();
			assertThat(round.isStrike()).isFalse();
			assertThat(round.isSpare()).isFalse();
		}
	}

	@Test
	void testStrike() {
		Round round = new NormalRound(0);
		try {
			round.addRoll(10);
			assertThat(round.getRoundIndex()).isEqualTo(0);
			assertThat(round.getRoll(0)).isEqualTo(10);
			assertThat(round.getRoll(1)).isEqualTo(-1);
			assertThat(round.getScore()).isEqualTo(10);
			assertThat(round.isRollAvailable()).isFalse();
			assertThat(round.isStrike()).isTrue();
			assertThat(round.isSpare()).isFalse();
		} catch (InvalidRollException e) {
			fail("Not expected");
		}
	}

	@Test
	void testStrikePlusone() {
		Round round = new NormalRound(0);
		try {
			round.addRoll(10);
			round.addRoll(3);
			fail("Don't do this");
		} catch (InvalidRollException e) {
			e.printStackTrace();
			assertThat(round.getRoundIndex()).isEqualTo(0);
			assertThat(round.getRoll(0)).isEqualTo(10);
			assertThat(round.getRoll(1)).isEqualTo(-1);
			assertThat(round.getScore()).isEqualTo(10);
			assertThat(round.isRollAvailable()).isFalse();
			assertThat(round.isStrike()).isTrue();
			assertThat(round.isSpare()).isFalse();
		}
	}

	@Test
	void testMorePins() {
		Round round = new NormalRound(0);
		try {
			round.addRoll(6);
			round.addRoll(6);
			fail("Don't do this");
		} catch (InvalidRollException e) {
			e.printStackTrace();
			assertThat(round.getRoundIndex()).isEqualTo(0);
			assertThat(round.getRoll(0)).isEqualTo(6);
			assertThat(round.getRoll(1)).isEqualTo(-1);
			assertThat(round.getScore()).isEqualTo(6);
			assertThat(round.isRollAvailable()).isTrue();
			assertThat(round.isStrike()).isFalse();
			assertThat(round.isSpare()).isFalse();
		}
	}

	@Test
	void testSpare() {
		Round round = new NormalRound(0);
		try {
			round.addRoll(2);
			round.addRoll(8);
			assertThat(round.getRoundIndex()).isEqualTo(0);
			assertThat(round.getRoll(0)).isEqualTo(2);
			assertThat(round.getRoll(1)).isEqualTo(8);
			assertThat(round.getScore()).isEqualTo(10);
			assertThat(round.isRollAvailable()).isFalse();
			assertThat(round.isStrike()).isFalse();
			assertThat(round.isSpare()).isTrue();
		} catch (InvalidRollException e) {
			fail("Not expected");
		}
	}

	@Test
	void testFinalRound() {
		Round round = new FinalRound(0);
		try {
			round.addRoll(4);
			round.addRoll(4);
			round.addRoll(4);
			assertThat(round.getRoundIndex()).isEqualTo(0);
			assertThat(round.getRoll(0)).isEqualTo(4);
			assertThat(round.getRoll(1)).isEqualTo(4);
			assertThat(round.getRoll(2)).isEqualTo(4);
			assertThat(round.getScore()).isEqualTo(12);
			assertThat(round.isRollAvailable()).isFalse();
			assertThat(round.isStrike()).isFalse();
			assertThat(round.isSpare()).isFalse();
		} catch (InvalidRollException e) {
			fail("Not expected");
		}

	}

	@Test
	void testFinalRound2Roll() {
		Round round = new FinalRound(9);
		try {
			round.addRoll(4);
			round.addRoll(4);
			assertThat(round.getRoundIndex()).isEqualTo(9);
			assertThat(round.getRoll(0)).isEqualTo(4);
			assertThat(round.getRoll(1)).isEqualTo(4);
			assertThat(round.getScore()).isEqualTo(8);
			assertThat(round.isRollAvailable()).isTrue();
			assertThat(round.isStrike()).isFalse();
			assertThat(round.isSpare()).isFalse();
		} catch (InvalidRollException e) {
			fail("Not expected");
		}
	}
}
