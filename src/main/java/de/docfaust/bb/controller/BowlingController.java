package de.docfaust.bb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.docfaust.bb.controller.exception.HTTPInvalidScoreException;
import de.docfaust.bb.model.exception.InvalidRollException;
import de.docfaust.bb.service.RoundService;

/**
 * Spring Controllerclass. Entry Point to the Java world
 * @author Werner Faust
 *
 */
@Controller
public class BowlingController {
	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Instance of RoundService.
	 */
	@Autowired
	private RoundService bowlingService;

	/**
	 * Route addRoll. This will add the given Roll score to the board.
	 * @param score Number of fallen pins.
	 * @param model Model to set the data for the UI
	 * @return name of page to show;
	 * @throws HTTPInvalidScoreException Thrown if it should be somehow possible to add unexpected data or invalid amount of pins.
	 */
	@GetMapping("/addroll")
	public String addRoll(@RequestParam(name = "score") int score, Model model) throws HTTPInvalidScoreException {
		logger.info("Adding Roll: " + score);
		try {
			bowlingService.addRoll(score);
		} catch (InvalidRollException e) {
			model.addAttribute("message", e.getMessage());
		}

		model.addAttribute("scoreboard", bowlingService.getScoreBoard());
		model.addAttribute("progress", bowlingService.isGameInProgress());
		return "index";
	}

	/**
	 * Route / 
	 * @param model Model to set the data for the UI
	 * @return name of page to show;
	 */
	@GetMapping("/")
	public String getScoreBoard(Model model) {
		logger.info("Retrieving Scoreboard");
		model.addAttribute("scoreboard", bowlingService.getScoreBoard());
		model.addAttribute("progress", bowlingService.isGameInProgress());
		return "index";
	}


	/**
	 * Route /reset. Resets the Board.
	 * @param model
	 * @param model Model to set the data for the UI
	 * @return name of page to show;
	 * @return
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public String reset(Model model) {
		logger.info("Resetting Scoreboard");
		bowlingService.initializeRounds();
		model.addAttribute("scoreboard", bowlingService.getScoreBoard());
		model.addAttribute("progress", bowlingService.isGameInProgress());
		return "index";
	}

}
