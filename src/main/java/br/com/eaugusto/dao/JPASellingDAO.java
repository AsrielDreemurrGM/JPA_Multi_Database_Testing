package br.com.eaugusto.dao;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.eaugusto.dao.generics.JPAGenericDAO;
import br.com.eaugusto.domain.JPAClient;
import br.com.eaugusto.domain.JPAProduct;
import br.com.eaugusto.domain.JPASelling;
import br.com.eaugusto.exceptions.DAOException;

/**
 * Concrete DAO implementation for {@link JPASelling}.
 * Overrides and extends base methods to manage business logic for sales,
 * including cascade operations and criteria queries.
 * 
 * Includes logic for:
 * - Registering sales with merged relationships;
 * - Preventing standard deletion;
 * - Fetching related collections eagerly.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 21, 2025
 */
public class JPASellingDAO extends JPAGenericDAO<JPASelling, Long> implements IJPASellingDAO {

    public JPASellingDAO() {
        super(JPASelling.class);
    }

    /**
     * Finalizes a sale by updating its status.
     *
     * @param sale The {@link JPASelling} entity to finalize.
     */
    @Override
    public void finalizeSale(JPASelling sale) {
        super.update(sale);
    }

    /**
     * Cancels a sale by updating its status.
     *
     * @param sale The {@link JPASelling} entity to cancel.
     */
    @Override
    public void cancelSale(JPASelling sale) {
        super.update(sale);
    }

    /**
     * Prevents deletion of sales due to business rules.
     *
     * @param entity The {@link JPASelling} entity.
     * @throws UnsupportedOperationException If deletion is attempted.
     */
    @Override
    public void delete(JPASelling entity) {
        throw new UnsupportedOperationException("Operation not allowed");
    }

	/**
	 * Used only for test cleanup purposes.
	 *
	 * @param entity The {@link JPASelling} entity to delete.
	 */
    public void testCleanupDelete(JPASelling entity) {
        super.delete(entity);
    }

    /**
     * Registers a new sale with cascaded client and product merge.
     *
     * @param entity The {@link JPASelling} entity to register.
     * @return The registered {@link JPASelling} entity.
     * @throws DAOException If any exception occurs during registration.
     */
    @Override
    public JPASelling register(JPASelling entity) {
        try {
            openConnection();
            entity.getProducts().forEach(productQuantity -> {
                JPAProduct productJpa = entityManager.merge(productQuantity.getProduct());
                productQuantity.setProduct(productJpa);
            });
            JPAClient client = entityManager.merge(entity.getClient());
            entity.setClient(client);
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            closeConnection();
            return entity;
        } catch (Exception exception) {
            throw new DAOException("Error saving sale", exception);
        }
    }

    /**
     * Retrieves a sale with all its collections loaded (client and products).
     *
     * @param id The ID of the sale.
     * @return The {@link JPASelling} entity with collections fetched.
     */
    @Override
    public JPASelling findWithCollections(Long id) {
        openConnection();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<JPASelling> query = builder.createQuery(JPASelling.class);
        Root<JPASelling> root = query.from(JPASelling.class);
        root.fetch("client");
        root.fetch("products");
        query.select(root).where(builder.equal(root.get("id"), id));
        TypedQuery<JPASelling> typedQuery = entityManager.createQuery(query);
        JPASelling sale = typedQuery.getSingleResult();
        closeConnection();
        return sale;
    }
}
