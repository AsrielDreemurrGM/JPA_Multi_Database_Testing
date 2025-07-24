package br.com.eaugusto.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import br.com.eaugusto.dao.generics.IJPAGenericDAO;
import br.com.eaugusto.dao.generics.JPAGenericDAO;
import br.com.eaugusto.exceptions.DAOException;

/**
 * Tests for the {@link JPAClient} entity and its DAO operations.
 * 
 * Covers registering, finding by ID, updating, deleting, and retrieving all clients.
 * Uses {@link IJPAGenericDAO} and {@link JPAGenericDAO}.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 21, 2025
 */
public class JPAClientTest {

    private final IJPAGenericDAO<JPAClient, Long> clientDao = new JPAGenericDAO<>(JPAClient.class);

    @Test
    public void registerAndFindByIdTest() throws DAOException {
        JPAClient client = new JPAClient();
        client.setName("Eduardo");
        client.setCpf("12345678900");
        client.setPhone("(11) 99999-1234");
        client.setAddress("Rua dos Testes");
        client.setAddressNumber("101");
        client.setCity("JPA City");
        client.setState("JPA State");

        JPAClient registeredClient = clientDao.register(client);
        assertNotNull(registeredClient.getId(), "Registered client should have an ID");

        JPAClient databaseClient = clientDao.findById(registeredClient.getId());
        assertNotNull(databaseClient, "Should find the saved client by ID");

        assertEquals(client.getName(), databaseClient.getName());
        assertEquals(client.getCpf(), databaseClient.getCpf());
        assertEquals(client.getPhone(), databaseClient.getPhone());
        assertEquals(client.getAddress(), databaseClient.getAddress());
        assertEquals(client.getAddressNumber(), databaseClient.getAddressNumber());
        assertEquals(client.getCity(), databaseClient.getCity());
        assertEquals(client.getState(), databaseClient.getState());

        Long oldId = databaseClient.getId();
        databaseClient.setId(oldId + 1);
        assertEquals(oldId + 1, databaseClient.getId());

        databaseClient.setId(oldId);

        clientDao.delete(databaseClient);

        JPAClient searchDatabaseClient = clientDao.findById(databaseClient.getId());
        assertNull(searchDatabaseClient, "Database client should be deleted");
        
        clientDao.delete(registeredClient);

        JPAClient searchRegisteredClient = clientDao.findById(registeredClient.getId());
        assertNull(searchRegisteredClient, "Registered client should be deleted");
    }

    @Test
    public void findAllTest() throws DAOException {
        JPAClient client1 = new JPAClient();
        client1.setName("Maria");
        client1.setCpf("11111111111");
        client1.setPhone("(11) 12345-6789");
        client1.setAddress("Rua A");
        client1.setAddressNumber("22");
        client1.setCity("City1");
        client1.setState("State1");

        JPAClient client2 = new JPAClient();
        client2.setName("João");
        client2.setCpf("22222222222");
        client2.setPhone("(11) 98765-4321");
        client2.setAddress("Rua B");
        client2.setAddressNumber("33");
        client2.setCity("City2");
        client2.setState("State2");

        clientDao.register(client1);
        clientDao.register(client2);

        Collection<JPAClient> allClients = clientDao.findAll();
        assertNotNull(allClients);
        assertEquals(2, allClients.size(), "Two clients should exist");

        clientDao.delete(client1);
        JPAClient searchClient1 = clientDao.findById(client1.getId());
        assertNull(searchClient1, "Client1 should be deleted");
        
        clientDao.delete(client2);
        JPAClient searchClient2 = clientDao.findById(client2.getId());
        assertNull(searchClient2, "Client2 should be deleted");
    }

    @Test
    public void updateTest() throws DAOException {
        JPAClient client = new JPAClient();
        client.setName("Lucas");
        client.setCpf("33333333333");
        client.setPhone("123456789");
        client.setAddress("Rua Java");
        client.setAddressNumber("12");
        client.setCity("HelloWorld");
        client.setState("Sun");

        JPAClient saved = clientDao.register(client);

        saved.setName("Lucas Silva");
        saved.setPhone("987654321");
        saved.setAddress("Rua SQL");
        saved.setAddressNumber("45");
        saved.setCity("Query");
        saved.setState("PostgreSQL");

        JPAClient updatedClient = clientDao.update(saved);

        assertEquals("Lucas Silva", updatedClient.getName());
        assertEquals("987654321", updatedClient.getPhone());
        assertEquals("Rua SQL", updatedClient.getAddress());
        assertEquals("45", updatedClient.getAddressNumber());
        assertEquals("Query", updatedClient.getCity());
        assertEquals("PostgreSQL", updatedClient.getState());

        clientDao.delete(updatedClient);
        JPAClient searchClient = clientDao.findById(updatedClient.getId());
        assertNull(searchClient, "Client should be deleted");
    }

    @Test
    public void findByIdNonExistentTest() throws DAOException {
        JPAClient client = clientDao.findById(-1L);
        assertNull(client);
    }
}
