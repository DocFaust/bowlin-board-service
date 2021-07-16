package de.docfaust.bb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.docfaust.bb.controller.exception.HTTPInvalidScoreException;
import de.docfaust.bb.dto.DTOScore;
import de.docfaust.bb.dto.DTOScoreBoard;
import de.docfaust.bb.model.exception.InvalidRollException;
import de.docfaust.bb.service.RoundService;

@Controller
public class BowlingController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RoundService bowlingService;

	@GetMapping("/")
	public String getScoreBoard(@RequestParam(name="score") int score, Model model) throws HTTPInvalidScoreException {
		try {
			bowlingService.addRoll(score);
		} catch (InvalidRollException e) {
			throw new HTTPInvalidScoreException(score);
		}

		DTOScoreBoard list = new DTOScoreBoard();
		list.setFrames(bowlingService.getScoreBoard());
		model.addAttribute("scoreboard", list.getFrames());
		return "index";
	}

//	@RequestMapping(value = "/scoreboard", method = RequestMethod.GET)
//	public DTOScoreBoard getScoreBoard() {
//		DTOScoreBoard list = new DTOScoreBoard();
//		list.setFrames(bowlingService.getScoreBoard());
//		return list;
//	}

	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public void reset() {
		bowlingService.initializeRounds();
	}

	@RequestMapping(value = "/scoreadd", method = RequestMethod.GET)
	public String addScore(@RequestParam(name="score") DTOScore score, Model model) throws HTTPInvalidScoreException {
		logger.info("Adding Score: " + score.getScore());
		try {
			bowlingService.addRoll(score.getScore());
		} catch (InvalidRollException e) {
			throw new HTTPInvalidScoreException(score.getScore());
		}
		return "index";
	}
}
