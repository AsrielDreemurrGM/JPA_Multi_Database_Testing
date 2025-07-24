package br.com.eaugusto.exceptions;

/**
 * Exception for general DAO-related errors.
 *
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 11, 2025
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
}
