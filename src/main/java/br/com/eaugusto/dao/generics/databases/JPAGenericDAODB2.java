package br.com.eaugusto.dao.generics.databases;

import java.io.Serializable;

import br.com.eaugusto.dao.generics.JPAGenericDAO;
import br.com.eaugusto.domain.IPersistable;

/**
 * Generic DAO for Database Unit 2 (PostgreSQL).
 * <p>
 * This class extends {@link JPAGenericDAO} and configures the persistence unit
 * as "Online_Selling_2", which also connects to a PostgreSQL database. Intended
 * for managing entity access through the second PostgreSQL persistence context.
 *
 * @param <T> the type of persistable entity
 * @param <E> the type of entity ID
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 24, 2025
 */
public abstract class JPAGenericDAODB2<T extends IPersistable, E extends Serializable> extends JPAGenericDAO<T, E> {

	protected JPAGenericDAODB2(Class<T> persistableClass) {
		super(persistableClass, "Online_Selling_2");
	}
}
