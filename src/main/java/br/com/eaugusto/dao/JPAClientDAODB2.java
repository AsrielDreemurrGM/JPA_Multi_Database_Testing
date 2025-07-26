package br.com.eaugusto.dao;

import br.com.eaugusto.dao.generics.JPAGenericDAO;
import br.com.eaugusto.dao.generics.databases.JPAGenericDAODB2;
import br.com.eaugusto.domain.JPAClient;

/**
 * Concrete DAO implementation for {@link JPAClient}. Uses {@link JPAGenericDAO}
 * as a base and applies client-specific logic.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 21, 2025
 */
public class JPAClientDAODB2 extends JPAGenericDAODB2<JPAClient, Long> implements IJPAClientDAO<JPAClient> {

	public JPAClientDAODB2() {
		super(JPAClient.class);
	}
}
