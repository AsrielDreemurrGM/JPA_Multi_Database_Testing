package br.com.eaugusto.domain;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import br.com.eaugusto.dao.IJPAClientDAO;
import br.com.eaugusto.dao.JPAClientDAODB1;
import br.com.eaugusto.dao.JPAClientDAODB2;
import br.com.eaugusto.dao.JPAClientDAODB3;
import br.com.eaugusto.exceptions.DAOException;

/**
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 27, 2025
 */
public class JPAClientThreeDatabasesTest {

	private IJPAClientDAO<JPAClient> clientDB1Dao;
	private IJPAClientDAO<JPAClient> clientDB2Dao;
	private IJPAClientDAO<JPAClient> clientMySqlDBDao;
	private List<IJPAClientDAO<JPAClient>> allDaos;
	private Random randomNumber;

	public JPAClientThreeDatabasesTest() {
		clientDB1Dao = new JPAClientDAODB1();
		clientDB2Dao = new JPAClientDAODB2();
		clientMySqlDBDao = new JPAClientDAODB3();
		allDaos = Arrays.asList(clientDB1Dao, clientDB2Dao, clientMySqlDBDao);
		randomNumber = new Random();
	}

	@AfterEach
	public void clearTestDatabases() throws DAOException {
		for (IJPAClientDAO<JPAClient> eachDao : allDaos) {
			delete(eachDao.findAll(), eachDao);
		}
	}

	private void delete(Collection<JPAClient> list, IJPAClientDAO<JPAClient> eachDao) {
		list.forEach(client -> {
			try {
				eachDao.delete(client);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void searchClientById() throws DAOException {
		for (IJPAClientDAO<JPAClient> eachDao : allDaos) {
			JPAClient client = createClient();
			eachDao.register(client);
			assertNotNull(eachDao.findById(client.getId()));
		}
	}

	@Test
	public void registerClient() throws DAOException {
		for (IJPAClientDAO<JPAClient> eachDao : allDaos) {
			JPAClient client = createClient();
			JPAClient registered = eachDao.register(client);
			assertNotNull(registered);
			assertNotNull(eachDao.findById(registered.getId()));
			eachDao.delete(client);
			assertNull(eachDao.findById(registered.getId()));
		}
	}

	@Test
	public void deleteClient() throws DAOException {
		for (IJPAClientDAO<JPAClient> eachDao : allDaos) {
			JPAClient client = createClient();
			JPAClient registered = eachDao.register(client);
			assertNotNull(registered);
			assertNotNull(eachDao.findById(client.getId()));
			eachDao.delete(client);
			assertNull(eachDao.findById(client.getId()));
		}
	}

	@Test
	public void updateClient() throws DAOException {
		for (IJPAClientDAO<JPAClient> eachDao : allDaos) {
			JPAClient client = createClient();
			JPAClient registered = eachDao.register(client);
			assertNotNull(registered);

			JPAClient dbClient = eachDao.findById(client.getId());
			assertNotNull(dbClient);

			dbClient.setName("Eduardo Augusto");
			dbClient.setCpf(randomNumber.ints().toString());
			dbClient.setPhone("7788888888");
			dbClient.setCity("JPA City");
			dbClient.setState("Java City");
			dbClient.setAddress("Generic Address");
			dbClient.setAddressNumber("404");

			eachDao.update(dbClient);

			JPAClient updated = eachDao.findById(dbClient.getId());
			assertNotNull(updated);
			assertEquals(dbClient.getName(), updated.getName());
			assertEquals(dbClient.getCpf(), updated.getCpf());
			assertEquals(dbClient.getPhone(), updated.getPhone());
			assertEquals(dbClient.getCity(), updated.getCity());
			assertEquals(dbClient.getState(), updated.getState());
			assertEquals(dbClient.getAddress(), updated.getAddress());
			assertEquals(dbClient.getAddressNumber(), updated.getAddressNumber());

			eachDao.delete(client);
			assertNull(eachDao.findById(updated.getId()));
		}
	}

	@Test
	public void findAll() throws DAOException {
		for (IJPAClientDAO<JPAClient> eachDao : allDaos) {
			JPAClient client1 = createClient();
			JPAClient client2 = createClient();

			eachDao.register(client1);
			eachDao.register(client2);

			Collection<JPAClient> list = eachDao.findAll();
			assertNotNull(list);
			assertEquals(2, list.size());

			list.forEach(client -> {
				try {
					eachDao.delete(client);
				} catch (DAOException e) {
					e.printStackTrace();
				}
			});

			Collection<JPAClient> cleared = eachDao.findAll();
			assertNotNull(cleared);
			assertTrue(cleared.isEmpty());
		}
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
