package de.docfaust.bb.model.exception;

/**
 * Exception indicating an invalid entry.
 * @author wfa339
 *
 */
public class InvalidRollException extends BowlingException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 432014619556766347L;

	public InvalidRollException(String msg) {
		super(msg);
	}
}
