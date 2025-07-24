package br.com.eaugusto.exceptions;

/**
 * Exception thrown when a database connection error occurs.
 * 
 * <p>
 * Wraps connection errors or missing environment variable issues.
 * </p>
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 11, 2025
 */
public class DatabaseConnectionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatabaseConnectionException(String message) {
		super(message);
	}

	public DatabaseConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}
