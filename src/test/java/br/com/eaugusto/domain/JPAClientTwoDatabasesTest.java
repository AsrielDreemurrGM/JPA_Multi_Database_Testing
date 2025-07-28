package br.com.eaugusto.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import br.com.eaugusto.dao.IJPAClientDAO;
import br.com.eaugusto.dao.JPAClientDAODB1;
import br.com.eaugusto.dao.JPAClientDAODB2;
import br.com.eaugusto.exceptions.DAOException;

/**
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 25, 2025
 */
public class JPAClientTwoDatabasesTest {

	private IJPAClientDAO<JPAClient> clientDB1Dao;

	private IJPAClientDAO<JPAClient> clientDB2Dao;

	private Random randomNumber;

	public JPAClientTwoDatabasesTest() {
		clientDB1Dao = new JPAClientDAODB1();
		clientDB2Dao = new JPAClientDAODB2();
		randomNumber = new Random();
	}

	@AfterEach
	public void clearTestDatabases() throws DAOException {
		Collection<JPAClient> database1ClientList = clientDB1Dao.findAll();
		clearDatabase1(database1ClientList);

		Collection<JPAClient> database2ClientList = clientDB2Dao.findAll();
		clearDatabase2(database2ClientList);
	}

	private void clearDatabase1(Collection<JPAClient> database1ClientList) {
		database1ClientList.forEach(client -> {
			try {
				clientDB1Dao.delete(client);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		});
	}

	private void clearDatabase2(Collection<JPAClient> database2ClientList) {
		database2ClientList.forEach(client -> {
			try {
				clientDB2Dao.delete(client);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void searchClientById() throws DAOException {
		JPAClient clientDB1 = createClient();
		clientDB1Dao.register(clientDB1);

		JPAClient clientFoundDB1 = clientDB1Dao.findById(clientDB1.getId());
		assertNotNull(clientFoundDB1);

		JPAClient clientDB2 = createClient();
		clientDB2Dao.register(clientDB2);

		JPAClient clientFoundDB2 = clientDB2Dao.findById(clientDB2.getId());
		assertNotNull(clientFoundDB2);
	}

	@Test
	public void registerClient() throws DAOException {
		JPAClient clientDB1 = createClient();
		JPAClient registeredClientDB1 = clientDB1Dao.register(clientDB1);
		assertNotNull(registeredClientDB1);

		JPAClient clientFoundDB1 = clientDB1Dao.findById(registeredClientDB1.getId());
		assertNotNull(clientFoundDB1);

		clientDB1Dao.delete(clientDB1);

		JPAClient clientDB2 = createClient();
		JPAClient searchClientDB1 = clientDB1Dao.findById(registeredClientDB1.getId());
		assertNull(searchClientDB1);

		JPAClient registeredClientDB2 = clientDB2Dao.register(clientDB2);
		assertNotNull(registeredClientDB2);

		JPAClient clientFoundDB2 = clientDB2Dao.findById(registeredClientDB2.getId());
		assertNotNull(clientFoundDB2);

		clientDB2Dao.delete(clientDB2);

		JPAClient searchClientDB2 = clientDB2Dao.findById(registeredClientDB2.getId());
		assertNull(searchClientDB2);
	}

	@Test
	public void deleteClient() throws DAOException {
		JPAClient clientDB1 = createClient();
		JPAClient registeredClientDB1 = clientDB1Dao.register(clientDB1);
		assertNotNull(registeredClientDB1);

		JPAClient database1Client = clientDB1Dao.findById(clientDB1.getId());
		assertNotNull(database1Client);

		clientDB1Dao.delete(clientDB1);
		database1Client = clientDB1Dao.findById(clientDB1.getId());
		assertNull(database1Client);

		JPAClient clientDB2 = createClient();
		JPAClient registeredClientDB2 = clientDB2Dao.register(clientDB2);
		assertNotNull(registeredClientDB2);

		JPAClient database2Client = clientDB2Dao.findById(clientDB2.getId());
		assertNotNull(database2Client);

		clientDB2Dao.delete(clientDB2);
		database2Client = clientDB2Dao.findById(clientDB2.getId());
		assertNull(database2Client);
	}

	@Test
	public void updateClient() throws DAOException {
		JPAClient clientDB1 = createClient();
		JPAClient registeredDB1Client = clientDB1Dao.register(clientDB1);
		assertNotNull(registeredDB1Client);

		JPAClient database1Client = clientDB1Dao.findById(clientDB1.getId());
		assertNotNull(database1Client);

		database1Client.setName("Eduardo Augusto");
		database1Client.setCpf(randomNumber.ints().toString());
		database1Client.setPhone("7788888888");
		database1Client.setCity("JPA City");
		database1Client.setState("Java City");
		database1Client.setAddress("Generic Address");
		database1Client.setAddressNumber("404");
		clientDB1Dao.update(database1Client);

		JPAClient updatedDB1Client = clientDB1Dao.findById(database1Client.getId());
		assertNotNull(updatedDB1Client);
		assertEquals(database1Client.getName(), updatedDB1Client.getName());
		assertEquals(database1Client.getCpf(), updatedDB1Client.getCpf());
		assertEquals(database1Client.getPhone(), updatedDB1Client.getPhone());
		assertEquals(database1Client.getCity(), updatedDB1Client.getCity());
		assertEquals(database1Client.getState(), updatedDB1Client.getState());
		assertEquals(database1Client.getAddress(), updatedDB1Client.getAddress());
		assertEquals(database1Client.getAddressNumber(), updatedDB1Client.getAddressNumber());

		clientDB1Dao.delete(clientDB1);
		database1Client = clientDB1Dao.findById(updatedDB1Client.getId());
		assertNull(database1Client);

		JPAClient clientDB2 = createClient();
		JPAClient registeredDB2Client = clientDB2Dao.register(clientDB2);
		assertNotNull(registeredDB2Client);

		JPAClient database2Client = clientDB2Dao.findById(clientDB2.getId());
		assertNotNull(database2Client);

		database2Client.setName("Eduardo Augusto");
		clientDB2Dao.update(database2Client);

		JPAClient updatedDB2Client = clientDB2Dao.findById(database2Client.getId());
		assertNotNull(updatedDB2Client);
		assertEquals(database2Client.getName(), updatedDB2Client.getName());
		assertEquals(database2Client.getCpf(), updatedDB2Client.getCpf());
		assertEquals(database2Client.getPhone(), updatedDB2Client.getPhone());
		assertEquals(database2Client.getCity(), updatedDB2Client.getCity());
		assertEquals(database2Client.getState(), updatedDB2Client.getState());
		assertEquals(database2Client.getAddress(), updatedDB2Client.getAddress());
		assertEquals(database2Client.getAddressNumber(), updatedDB2Client.getAddressNumber());

		clientDB2Dao.delete(clientDB2);
		database2Client = clientDB2Dao.findById(updatedDB2Client.getId());
		assertNull(database2Client);
	}

	@Test
	public void findAll() throws DAOException {
		JPAClient client1DB1 = createClient();
		JPAClient registeredDB1Client1 = clientDB1Dao.register(client1DB1);
		assertNotNull(registeredDB1Client1);

		JPAClient client2DB1 = createClient();
		JPAClient registeredDB1Client2 = clientDB1Dao.register(client2DB1);
		assertNotNull(registeredDB1Client2);

		Collection<JPAClient> clientDB1List = clientDB1Dao.findAll();
		assertTrue(clientDB1List != null);
		assertTrue(clientDB1List.size() == 2);

		clientDB1List.forEach(client -> {
			try {
				clientDB1Dao.delete(client);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		});

		Collection<JPAClient> findAllDB1Clients = clientDB1Dao.findAll();
		assertTrue(findAllDB1Clients != null);
		assertTrue(findAllDB1Clients.isEmpty());

		JPAClient client1DB2 = createClient();
		JPAClient registeredDB2Client1 = clientDB2Dao.register(client1DB2);
		assertNotNull(registeredDB2Client1);

		JPAClient client2DB2 = createClient();
		JPAClient registeredDB2Client2 = clientDB2Dao.register(client2DB2);
		assertNotNull(registeredDB2Client2);

		Collection<JPAClient> clientDB2List = clientDB2Dao.findAll();
		assertTrue(clientDB2List != null);
		assertTrue(clientDB2List.size() == 2);

		clientDB2List.forEach(client -> {
			try {
				clientDB2Dao.delete(client);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		});

		Collection<JPAClient> findAllDB2Clients = clientDB2Dao.findAll();
		assertTrue(findAllDB2Clients != null);
		assertTrue(findAllDB2Clients.isEmpty());
	}

	private JPAClient createClient() {
		JPAClient client = new JPAClient();
		client.setCpf(String.valueOf(randomNumber.nextInt(1_000_000_000)));
		client.setName("Eduardo");
		client.setCity("JDBC City");
		client.setAddress("SQL");
		client.setAddressNumber("403");
		client.setState("JPA");
		client.setPhone("4499999999");
		return client;
	}
}
