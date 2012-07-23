package solubris.marketmon.requestor;

public class PermanentRequestorException extends RequestorException {

	private static final long serialVersionUID = 1L;

	public PermanentRequestorException() {
		super();
	}

	public PermanentRequestorException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermanentRequestorException(String message) {
		super(message);
	}

	public PermanentRequestorException(Throwable cause) {
		super(cause);
	}
}
