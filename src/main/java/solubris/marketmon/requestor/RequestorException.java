package solubris.marketmon.requestor;

public abstract class RequestorException extends Exception {

	private static final long serialVersionUID = 1L;

	public RequestorException() {
		super();
	}

	public RequestorException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestorException(String message) {
		super(message);
	}

	public RequestorException(Throwable cause) {
		super(cause);
	}

}
