package br.com.eaugusto.exceptions;

/**
 * Exception for errors while setting parameters in DAO operations.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 11, 2025
 */
public class DAOParameterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DAOParameterException(String message) {
		super(message);
	}

	public DAOParameterException(String message, Throwable cause) {
		super(message, cause);
	}
}
