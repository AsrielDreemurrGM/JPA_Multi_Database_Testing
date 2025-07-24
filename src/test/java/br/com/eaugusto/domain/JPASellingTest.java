package br.com.eaugusto.domain;

import br.com.eaugusto.dao.JPAClientDAO;
import br.com.eaugusto.dao.JPAProductDAO;
import br.com.eaugusto.dao.JPASellingDAO;
import br.com.eaugusto.exceptions.DAOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Integration and unit tests for {@link JPASelling} entity and related DAOs.
 * 
 * Covers:
 * <ul>
 *  <li>Registering sales and fetching with collections</li>
 *  <li>Testing status ENUM retrieval</li>
 *  <li>Adding, removing, and updating products within sales</li>
 *  <li>Validation of business rules such as preventing modifications to finished sales</li>
 *  <li>Testing cancellation and exception handling on unsupported operations</li>
 * </ul>
 * 
 * Uses {@link JPASellingDAO}, {@link JPAClientDAO}, and {@link JPAProductDAO}.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 22, 2025
 */
public class JPASellingTest {

    private JPASellingDAO sellingDAO;
    private JPAClientDAO clientDAO;
    private JPAProductDAO productDAO;

    @BeforeEach
    public void setUp() {
        sellingDAO = new JPASellingDAO();
        clientDAO = new JPAClientDAO();
        productDAO = new JPAProductDAO();
    }

    @Test
    public void testRegisterAndFetchSelling() {
        JPAClient client = new JPAClient();
        client.setName("Test Client");
        client.setCpf("99999999999");
        client.setPhone("00000-0000");
        client.setAddress("Test Street");
        client.setAddressNumber("1");
        client.setCity("Test City");
        client.setState("Test State");
        clientDAO.register(client);

        JPAProduct product = new JPAProduct();
        product.setCode("TEST123");
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("10.00"));
        productDAO.register(product);

        JPASelling selling = new JPASelling();
        selling.setCode("SELLTEST1");
        selling.setClient(client);
        selling.setDateSold(Instant.now());
        selling.setSellingStatus(JPASelling.Status.STARTED);
        selling.addProduct(product, 3);

        sellingDAO.register(selling);
        Long sellingId = selling.getId();

        JPASelling fetchedSelling = sellingDAO.findWithCollections(sellingId);

        assertNotNull(fetchedSelling);
        assertEquals(selling.getCode(), fetchedSelling.getCode());
        assertEquals(client.getCpf(), fetchedSelling.getClient().getCpf());
        assertEquals(selling.getProducts().size(), fetchedSelling.getProducts().size());
        assertEquals(selling.getTotalPrice(), fetchedSelling.getTotalPrice());
        assertEquals(selling.getTotalProductQuantity(), fetchedSelling.getTotalProductQuantity());

        selling.removeAllProducts();
        sellingDAO.finalizeSale(selling);

        JPASelling searchSelling = sellingDAO.findWithCollections(sellingId);
        sellingDAO.testCleanupDelete(searchSelling);
        clientDAO.delete(client);
        productDAO.delete(product);
    }
    
    @Test
    public void testStatusGetByName() {
        assertEquals(JPASelling.Status.STARTED, JPASelling.Status.getByName("started"));
        assertEquals(JPASelling.Status.FINISHED, JPASelling.Status.getByName("FINISHED"));
        assertNull(JPASelling.Status.getByName("nonexistent"));
    }
    
    @Test
    public void testFindById() {
        JPAClient client = new JPAClient();
        client.setName("Generic Test");
        client.setCpf("12345678900");
        client.setPhone("00000-0000");
        client.setAddress("Street");
        client.setAddressNumber("10");
        client.setCity("City");
        client.setState("State");

        JPAClientDAO dao = new JPAClientDAO();
        dao.register(client);

        JPAClient found = dao.findById(client.getId());
        assertNotNull(found);
        assertEquals("Generic Test", found.getName());

        dao.delete(client);
    }
    
    @Test
    public void testFindAllReturnsNonEmptyList() {
        JPAProduct product = new JPAProduct();
        product.setCode("GEN123");
        product.setName("Generic Product");
        product.setDescription("Generic Description");
        product.setPrice(new BigDecimal("9.99"));

        JPAProductDAO dao = new JPAProductDAO();
        dao.register(product);

        Collection<JPAProduct> all = dao.findAll();
        assertFalse(all.isEmpty());

        dao.delete(product);
    }

    @Test
    public void testBasicGettersSetters() {
        JPASelling selling = new JPASelling();
        selling.setId(100L);
        selling.setTotalPrice(new BigDecimal("123.45"));
        selling.setDateSold(Instant.EPOCH);
        selling.setSellingStatus(JPASelling.Status.CANCELLED);

        assertEquals(selling.getId(), selling.getId());
        assertEquals(selling.getTotalPrice(), selling.getTotalPrice());
        assertEquals(selling.getDateSold(), selling.getDateSold());
        assertEquals(JPASelling.Status.CANCELLED, selling.getSellingStatus());
    }
    
    @Test
    public void testSetProducts() {
        JPASelling selling = new JPASelling();
        Set<JPAProductQuantity> set = new HashSet<>();
        selling.setProducts(set);
        assertEquals(set, selling.getProducts());
    }
    
    @Test
    public void testRemoveProductLogic() {
        JPASelling selling = new JPASelling();
        selling.setCode("REMOVE001");
        selling.setClient(new JPAClient());
        selling.setDateSold(Instant.now());
        selling.setSellingStatus(JPASelling.Status.STARTED);

        JPAProduct product = new JPAProduct();
        product.setCode("REM123");
        product.setName("Product");
        product.setDescription("...");
        product.setPrice(new BigDecimal("5.00"));

        selling.addProduct(product, 5);
        assertEquals(1, selling.getProducts().size(), "There should be one product");

        selling.removeProduct(product, 2);
        assertEquals(1, selling.getProducts().size(), "There should still be one product");

        selling.removeProduct(product, 3);
        assertEquals(0, selling.getProducts().size(), "There shouldn't be a product");
    }
    
    @Test
    public void testModifyWhenFinishedThrowsException() {
        JPASelling selling = new JPASelling();
        selling.setSellingStatus(JPASelling.Status.FINISHED);

        JPAProduct product = new JPAProduct();
        product.setCode("X123"); 
        product.setPrice(new BigDecimal("25.00"));

        assertThrows(UnsupportedOperationException.class, () -> selling.addProduct(product, 1));

        assertThrows(UnsupportedOperationException.class, () -> selling.removeProduct(product, 1));

        assertThrows(UnsupportedOperationException.class, selling::removeAllProducts);
    }
    
    @Test
    public void testRemoveProductThatDoesNotExist() {
        JPASelling selling = new JPASelling();
        selling.setSellingStatus(JPASelling.Status.STARTED);

        JPAProduct product = new JPAProduct();
        product.setCode("DOES_NOT_EXIST");

        selling.removeProduct(product, 1);

        assertEquals(0, selling.getProducts().size());
    }
    
    @Test
    public void testAddProductIncreasesQuantityIfAlreadyPresent() {
        JPASelling selling = new JPASelling();
        selling.setSellingStatus(JPASelling.Status.STARTED);

        JPAProduct product = new JPAProduct();
        product.setCode("DUPLICATE");
        product.setPrice(new BigDecimal("15.00"));

        selling.addProduct(product, 2);
        selling.addProduct(product, 3);

        assertEquals(1, selling.getProducts().size(), "Should only have one product entry");
        JPAProductQuantity productQuantity = selling.getProducts().iterator().next();
        assertEquals(5, productQuantity.getQuantity(), "Quantity should have been incremented");
    }
    
    @Test
    public void testCancelSaleUpdatesEntity() {
        JPAClient client = new JPAClient();
        client.setName("Cancel Client");
        client.setCpf("11122233344");
        client.setPhone("12345-6789");
        client.setAddress("Cancel Street");
        client.setAddressNumber("99");
        client.setCity("Cancel City");
        client.setState("Cancel State");
        clientDAO.register(client);

        JPAProduct product = new JPAProduct();
        product.setCode("CANCELPROD");
        product.setName("Cancel Product");
        product.setDescription("Desc");
        product.setPrice(new BigDecimal("20.00"));
        productDAO.register(product);

        JPASelling selling = new JPASelling();
        selling.setCode("CANCEL001");
        selling.setClient(client);
        selling.setDateSold(Instant.now());
        selling.setSellingStatus(JPASelling.Status.STARTED);
        selling.addProduct(product, 1);

        sellingDAO.register(selling);

        selling.setSellingStatus(JPASelling.Status.CANCELLED);
        sellingDAO.cancelSale(selling);

        JPASelling updated = sellingDAO.findWithCollections(selling.getId());
        assertEquals(JPASelling.Status.CANCELLED, updated.getSellingStatus());

        sellingDAO.testCleanupDelete(updated);
        clientDAO.delete(client);
        productDAO.delete(product);
    }
    
    @Test
    public void testDeleteThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> sellingDAO.delete(new JPASelling()));
    }
    
    @Test
    public void testRegisterWithInvalidDataThrowsDAOException() {
        JPASelling invalidSelling = new JPASelling();
        assertThrows(DAOException.class, () -> sellingDAO.register(invalidSelling));
    }
}
