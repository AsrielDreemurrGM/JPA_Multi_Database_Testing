package br.com.eaugusto.dao;

import br.com.eaugusto.dao.generics.JPAGenericDAO;
import br.com.eaugusto.domain.JPAProduct;

/**
 * Concrete DAO implementation for {@link JPAProduct}.
 * Uses {@link JPAGenericDAO} as a base and applies product-specific logic.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 21, 2025
 */
public class JPAProductDAO extends JPAGenericDAO<JPAProduct, Long> implements IJPAProductDAO {

    public JPAProductDAO() {
        super(JPAProduct.class);
    }
}
