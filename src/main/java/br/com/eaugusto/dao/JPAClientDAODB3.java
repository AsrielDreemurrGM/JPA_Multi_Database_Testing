package br.com.eaugusto.dao;

import br.com.eaugusto.dao.generics.JPAGenericDAO;
import br.com.eaugusto.dao.generics.databases.JPAGenericDAODB3;
import br.com.eaugusto.domain.JPAClient;

/**
 * Concrete DAO implementation for {@link JPAClient}. Uses {@link JPAGenericDAO}
 * as a base and applies client-specific logic.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 21, 2025
 */
public class JPAClientDAODB3 extends JPAGenericDAODB3<JPAClient, Long> implements IJPAClientDAO<JPAClient> {

	public JPAClientDAODB3() {
		super(JPAClient.class);
	}
}
