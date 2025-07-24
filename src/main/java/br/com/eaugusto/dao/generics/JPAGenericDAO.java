package br.com.eaugusto.dao.generics;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.eaugusto.domain.IPersistable;
import br.com.eaugusto.exceptions.DAOException;
import br.com.eaugusto.exceptions.DAOParameterException;
import br.com.eaugusto.exceptions.DatabaseConnectionException;

/**
 * Generic JPA DAO Implementation.
 * <p>
 * Provides a reusable implementation of {@link IJPAGenericDAO} with basic 
 * JPA operations and connection management. Works with any entity type 
 * implementing {@link IPersistable}.
 *
 * @param <T> the type of entity
 * @param <E> the type of entity ID
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 21, 2025
 */
public class JPAGenericDAO<T extends IPersistable, E extends Serializable> implements IJPAGenericDAO<T, E> {

    private static final String DEFAULTPERSISTENCEUNITNAME = "JPA_Multi_Database_Testing";

    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    private Class<T> entityClass;
    private String persistenceUnitName;

    public JPAGenericDAO(Class<T> entityClass, String persistenceUnitName) {
        this.entityClass = entityClass;
        this.persistenceUnitName = persistenceUnitName;
    }

    public JPAGenericDAO(Class<T> entityClass) {
        this(entityClass, DEFAULTPERSISTENCEUNITNAME);
    }

    @Override
    public T register(T entity) throws DAOException, DAOParameterException, DatabaseConnectionException {
        if (entity == null) {
            throw new DAOParameterException("Cannot register a null entity.");
        }

        try {
            openConnection();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            throw new DAOException("Error registering entity.", e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(T entity) throws DAOException, DAOParameterException, DatabaseConnectionException {
        if (entity == null) {
            throw new DAOParameterException("Cannot delete a null entity.");
        }

        try {
            openConnection();
            entity = entityManager.merge(entity);
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new DAOException("Error deleting entity.", e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public T update(T entity) throws DAOException, DAOParameterException, DatabaseConnectionException {
        if (entity == null) {
            throw new DAOParameterException("Cannot update a null entity.");
        }

        try {
            openConnection();
            T updated = entityManager.merge(entity);
            entityManager.getTransaction().commit();
            return updated;
        } catch (Exception e) {
            throw new DAOException("Error updating entity.", e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public T findById(E id) throws DAOException, DAOParameterException, DatabaseConnectionException {
        if (id == null) {
            throw new DAOParameterException("ID cannot be null.");
        }

        try {
            openConnection();
            T entity = entityManager.find(this.entityClass, id);
            entityManager.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            throw new DAOException("Error finding entity by ID.", e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public Collection<T> findAll() throws DAOException, DatabaseConnectionException {
        try {
            openConnection();
            return entityManager
                .createQuery(getSelectSql(), this.entityClass)
                .getResultList();
        } catch (Exception e) {
            throw new DAOException("Error fetching all entities.", e);
        } finally {
            closeConnection();
        }
    }

    /**
     * Opens the EntityManager and begins a transaction.
     *
     * @throws DatabaseConnectionException if the EntityManager or Factory fails to initialize
     */
    protected void openConnection() throws DatabaseConnectionException {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(getPersistenceUnitName());
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
        } catch (Exception e) {
            throw new DatabaseConnectionException("Failed to open JPA connection.", e);
        }
    }

    /**
     * Closes the EntityManager and its factory safely.
     *
     * @throws DatabaseConnectionException if an error occurs while closing resources
     */
    protected void closeConnection() throws DatabaseConnectionException {
        try {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
                entityManagerFactory.close();
            }
        } catch (Exception e) {
            throw new DatabaseConnectionException("Failed to close JPA connection.", e);
        }
    }

    /**
     * Builds the JPQL select query string for the given entity class.
     *
     * @return JPQL query string to fetch all entities
     */
    private String getSelectSql() {
        return "SELECT obj FROM " + this.entityClass.getSimpleName() + " obj";
    }

    /**
     * Returns the configured persistence unit name.
     *
     * @return the persistence unit name to be used in the connection
     */
    private String getPersistenceUnitName() {
        return persistenceUnitName;
    }
}
