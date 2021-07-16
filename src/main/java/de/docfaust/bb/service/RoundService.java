package de.docfaust.bb.service;

import java.util.List;

import de.docfaust.bb.model.ScoreFrame;
import de.docfaust.bb.model.exception.InvalidRollException;

public interface RoundService {

	void initializeRounds();

	void addRoll(int score) throws InvalidRollException;

	List<ScoreFrame> getScoreBoard();

}