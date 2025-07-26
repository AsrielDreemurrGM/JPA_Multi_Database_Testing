package br.com.eaugusto.dao.generics.databases;

import java.io.Serializable;

import br.com.eaugusto.dao.generics.JPAGenericDAO;
import br.com.eaugusto.domain.IPersistable;

/**
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 24, 2025
 */
public abstract class JPAGenericDAODB2<T extends IPersistable, E extends Serializable> extends JPAGenericDAO<T, E> {

	protected JPAGenericDAODB2(Class<T> persistableClass) {
		super(persistableClass, "Online_Selling_2");
	}
}
