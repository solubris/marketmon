package solubris.marketmon.requestor;

public class TemporaryRequestorException extends RequestorException {

	private static final long serialVersionUID = 1L;

	public TemporaryRequestorException() {
		super();
	}

	public TemporaryRequestorException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemporaryRequestorException(String message) {
		super(message);
	}

	public TemporaryRequestorException(Throwable cause) {
		super(cause);
	}
}
