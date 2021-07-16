package de.docfaust.bb.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class HTTPInvalidScoreException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1808892194550053270L;

	public HTTPInvalidScoreException(int score) {
		super("Invalid Score " + score);
	}
}
