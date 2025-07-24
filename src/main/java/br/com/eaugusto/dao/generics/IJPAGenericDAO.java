package br.com.eaugusto.dao.generics;

import java.io.Serializable;
import java.util.Collection;

import br.com.eaugusto.domain.IPersistable;
import br.com.eaugusto.exceptions.DAOException;
import br.com.eaugusto.exceptions.DAOParameterException;
import br.com.eaugusto.exceptions.DatabaseConnectionException;

/**
 * Generic DAO Interface for JPA-Based Persistence.
 * <p>
 * Provides a contract for common CRUD operations using Java Persistence API (JPA).
 * Designed to work with entities implementing {@link IPersistable}.
 *
 * @param <T> the type of entity (must implement IPersistable)
 * @param <E> the type of the entity ID (must be Serializable)
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 21, 2025
 */
public interface IJPAGenericDAO<T extends IPersistable, E extends Serializable> {

    /**
     * Registers (persists) a new entity in the database.
     *
     * @param entity the entity to be persisted
     * @return the persisted entity
     * @throws DAOException if a persistence error occurs
     * @throws DAOParameterException if the entity is null or invalid
     * @throws DatabaseConnectionException if there is a problem connecting to the database
     */
    public T register(T entity) throws DAOException, DAOParameterException, DatabaseConnectionException;

    /**
     * Deletes the given entity from the database.
     *
     * @param entity the entity to be removed
     * @throws DAOException if a persistence error occurs
     * @throws DAOParameterException if the entity is null
     * @throws DatabaseConnectionException if there is a problem connecting to the database
     */
    public void delete(T entity) throws DAOException, DAOParameterException, DatabaseConnectionException;

    /**
     * Updates the given entity in the database.
     *
     * @param entity the entity to be updated
     * @return the updated entity
     * @throws DAOException if a persistence error occurs
     * @throws DAOParameterException if the entity is null
     * @throws DatabaseConnectionException if there is a problem connecting to the database
     */
    public T update(T entity) throws DAOException, DAOParameterException, DatabaseConnectionException;

    /**
     * Finds an entity by its identifier.
     *
     * @param id the entity ID
     * @return the found entity or null if not found
     * @throws DAOException if a persistence error occurs
     * @throws DAOParameterException if the ID is null
     * @throws DatabaseConnectionException if there is a problem connecting to the database
     */
    public T findById(E id) throws DAOException, DAOParameterException, DatabaseConnectionException;

    /**
     * Retrieves all entities of type T from the database.
     *
     * @return a collection of all entities
     * @throws DAOException if a persistence error occurs
     * @throws DatabaseConnectionException if there is a problem connecting to the database
     */
    public Collection<T> findAll() throws DAOException, DatabaseConnectionException;
}
