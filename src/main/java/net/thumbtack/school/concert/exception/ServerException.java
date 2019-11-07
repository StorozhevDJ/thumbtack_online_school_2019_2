package net.thumbtack.school.concert.exception;

public class ServerException extends Exception {

	private static final long serialVersionUID = -7473999065338693839L;

	private ServerErrorCode serverErrorCode;
	private String serverErrorText;

	public ServerException(ServerErrorCode serverErrorCode, String param) {
		serverErrorText = new String(String.format(serverErrorCode.getText(), param));
		this.serverErrorCode = serverErrorCode;
	}

	public ServerException(ServerErrorCode serverErrorCode) {
		this(serverErrorCode, "");
	}

	public ServerException(String message) {
		this(ServerErrorCode.OTHER_ERROR, message);
		// super(message);
	}

	/**
	 * Get server error text code
	 * 
	 * @return String text error code
	 */
	public String getServerErrorText() {
		return serverErrorText;
	}

	/**
	 * Get Server Error Code w/o text
	 * @return ServerErrorCode
	 */
	public ServerErrorCode getServerErrorCode() {
		return serverErrorCode;
	}

}