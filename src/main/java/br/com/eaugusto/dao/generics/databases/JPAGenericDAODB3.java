package br.com.eaugusto.dao.generics.databases;

import java.io.Serializable;

import br.com.eaugusto.dao.generics.JPAGenericDAO;
import br.com.eaugusto.domain.IPersistable;

/**
 * Generic DAO for Database Unit 3 (MySQL).
 * <p>
 * This class extends {@link JPAGenericDAO} and sets up the persistence unit as
 * "My_SQL_Database", which is configured to connect to a MySQL database. It
 * enables seamless use of JPA operations over entities stored in the MySQL
 * database.
 *
 * @param <T> the type of persistable entity
 * @param <E> the type of entity ID
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 24, 2025
 */
public abstract class JPAGenericDAODB3<T extends IPersistable, E extends Serializable> extends JPAGenericDAO<T, E> {

	protected JPAGenericDAODB3(Class<T> persistableClass) {
		super(persistableClass, "My_SQL_Database");
	}
}
