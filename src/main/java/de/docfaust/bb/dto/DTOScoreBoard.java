package de.docfaust.bb.dto;

import java.util.ArrayList;
import java.util.List;

import de.docfaust.bb.model.ScoreFrame;

public class DTOScoreBoard {
	private List<ScoreFrame> frames = new ArrayList<>();

	public List<ScoreFrame> getFrames() {
		return frames;
	}

	public void setFrames(List<ScoreFrame> frames) {
		this.frames = frames;
	}
	
}
