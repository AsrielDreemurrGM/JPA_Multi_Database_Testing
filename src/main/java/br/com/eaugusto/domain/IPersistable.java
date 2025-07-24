package br.com.eaugusto.domain;

/**
 * Interface to be implemented by all persistable JPA entities.
 * Provides access to the entity's identifier for generic DAO operations.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 21, 2025
 */
public interface IPersistable {

    /**
     * Returns the unique identifier of the entity.
     * 
     * @return the ID of the entity.
     */
    public Long getId();

    /**
     * Sets the unique identifier of the entity.
     * 
     * @param id the ID to set.
     */
    public void setId(Long id);
}
