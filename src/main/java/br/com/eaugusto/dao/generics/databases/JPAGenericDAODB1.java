package br.com.eaugusto.dao.generics.databases;

import java.io.Serializable;

import br.com.eaugusto.dao.generics.JPAGenericDAO;
import br.com.eaugusto.domain.IPersistable;

/**
 * Generic DAO for Database Unit 1 (PostgreSQL).
 * <p>
 * This class extends {@link JPAGenericDAO} and configures the persistence unit
 * as "JPA_Multi_Database_Testing", which connects to a PostgreSQL database. It
 * serves as a reusable DAO base class for entity operations routed through the
 * first persistence unit.
 *
 * @param <T> the type of persistable entity
 * @param <E> the type of entity ID
 *
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 24, 2025
 */
public abstract class JPAGenericDAODB1<T extends IPersistable, E extends Serializable> extends JPAGenericDAO<T, E> {

	protected JPAGenericDAODB1(Class<T> persistableClass) {
		super(persistableClass, "JPA_Multi_Database_Testing");
	}
}
