package br.com.eaugusto.exceptions;

/**
 * Exception thrown when entity mapping or annotation-based DAO operations fail.
 * 
 * Indicates errors in reflection, missing annotations, or database mapping
 * issues.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 11, 2025
 */
public class EntityMappingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityMappingException(String message) {
		super(message);
	}

	public EntityMappingException(String message, Throwable cause) {
		super(message, cause);
	}
}
