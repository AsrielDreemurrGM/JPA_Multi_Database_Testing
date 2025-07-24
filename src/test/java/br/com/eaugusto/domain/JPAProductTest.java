package br.com.eaugusto.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import br.com.eaugusto.dao.generics.IJPAGenericDAO;
import br.com.eaugusto.dao.generics.JPAGenericDAO;
import br.com.eaugusto.exceptions.DAOException;

/**
 * Tests for the {@link JPAProduct} entity and its DAO functionality.
 * 
 * Includes tests for registering, finding by ID, updating, deleting, and retrieving all products.
 * Utilizes {@link IJPAGenericDAO} and {@link JPAGenericDAO}.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 21, 2025
 */
public class JPAProductTest {

    private final IJPAGenericDAO<JPAProduct, Long> productDao = new JPAGenericDAO<>(JPAProduct.class);

    @Test
    public void registerAndFindByIdTest() throws DAOException {
        JPAProduct product = new JPAProduct();
        product.setCode("P1001");
        product.setName("Test-Wooden-Table");
        product.setDescription("Four-Legged Wooden Table");
        product.setPrice(new BigDecimal("99.99"));

        JPAProduct registeredProduct = productDao.register(product);
        assertNotNull(registeredProduct.getId(), "Registered product should have an ID");

        JPAProduct databaseProduct = productDao.findById(registeredProduct.getId());
        assertNotNull(databaseProduct, "Should find the saved product by ID");
        assertEquals(product.getCode(), databaseProduct.getCode());
        assertEquals(product.getName(), databaseProduct.getName());
        assertEquals(product.getDescription(), databaseProduct.getDescription());
        assertEquals(product.getPrice(), databaseProduct.getPrice());

        Long oldId = databaseProduct.getId();
        databaseProduct.setId(oldId + 1);
        assertEquals(oldId + 1, databaseProduct.getId());

        databaseProduct.setId(oldId);

        productDao.delete(registeredProduct);

        JPAProduct searchRegisteredProduct = productDao.findById(registeredProduct.getId());
        assertNull(searchRegisteredProduct, "Registered product should be deleted");
        
        productDao.delete(databaseProduct);

        JPAProduct searchDatabaseProduct = productDao.findById(databaseProduct.getId());
        assertNull(searchDatabaseProduct, "Database product should be deleted");
    }

    @Test
    public void findAllTest() throws DAOException {
        JPAProduct product1 = new JPAProduct();
        product1.setCode("P2001");
        product1.setName("Test Wooden Cabinet");
        product1.setDescription("Maple Wood Cabinet");
        product1.setPrice(new BigDecimal("150.00"));

        JPAProduct product2 = new JPAProduct();
        product2.setCode("P2002");
        product2.setName("Test Wooden Chair");
        product2.setDescription("Three-Legged Chair");
        product2.setPrice(new BigDecimal("49.99"));

        productDao.register(product1);
        productDao.register(product2);

        Collection<JPAProduct> allProducts = productDao.findAll();
        assertNotNull(allProducts);
        assertEquals(2, allProducts.size(), "Two products should exist");

        productDao.delete(product1);
        JPAProduct searchProduct1 = productDao.findById(product1.getId());
        assertNull(searchProduct1, "Product1 should be deleted");
        
        productDao.delete(product2);
        JPAProduct searchProduct2 = productDao.findById(product2.getId());
        assertNull(searchProduct2, "Product2 should be deleted");
    }

    @Test
    public void updateTest() throws DAOException {
        JPAProduct product = new JPAProduct();
        product.setCode("P3001");
        product.setName("Test Wood Couch");
        product.setDescription("Couch Made Of Hard Wood");
        product.setPrice(new BigDecimal("200.00"));

        JPAProduct registeredProduct = productDao.register(product);

        registeredProduct.setName("Test Wooden Couch");
        registeredProduct.setPrice(new BigDecimal("210.00"));
        registeredProduct.setDescription("Couch made with premium hardwood");

        JPAProduct updatedProduct = productDao.update(registeredProduct);

        assertEquals(product.getName(), updatedProduct.getName());
        assertEquals(product.getPrice(), updatedProduct.getPrice());
        assertEquals(product.getDescription(), updatedProduct.getDescription());

        productDao.delete(updatedProduct);
        JPAProduct searchProduct = productDao.findById(updatedProduct.getId());
        assertNull(searchProduct, "Product should be deleted");
    }

    @Test
    public void findByIdNonExistentTest() throws DAOException {
        JPAProduct product = productDao.findById(-1L);
        assertNull(product, "Finding non-existent product should return null");
    }
}
