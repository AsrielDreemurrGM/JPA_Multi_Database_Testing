package br.com.eaugusto.exceptions;

/**
 * Exception thrown when an error occurs while mapping inventory data from a
 * database result set.
 * 
 * Used by InventoryDAO's helper method to indicate invalid data or result
 * issues.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 12, 2025
 */
public class InventoryMappingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InventoryMappingException(String message) {
		super(message);
	}

	public InventoryMappingException(String message, Throwable cause) {
		super(message, cause);
	}
}
