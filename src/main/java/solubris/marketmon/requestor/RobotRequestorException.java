package solubris.marketmon.requestor;

/**
 * Robot was detected, so site blocked request
 * 
 * @author walterst
 */
public class RobotRequestorException extends TemporaryRequestorException {

	private static final long serialVersionUID = 1L;

	public RobotRequestorException() {
		super();
	}

	public RobotRequestorException(String message, Throwable cause) {
		super(message, cause);
	}

	public RobotRequestorException(String message) {
		super(message);
	}

	public RobotRequestorException(Throwable cause) {
		super(cause);
	}
}
