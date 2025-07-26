package br.com.eaugusto.dao;

import br.com.eaugusto.dao.generics.IJPAGenericDAO;
import br.com.eaugusto.domain.IPersistable;
import br.com.eaugusto.domain.JPAClient;

/**
 * DAO interface for {@link JPAClient} entity. Extends the generic DAO interface
 * with Long as the identifier type.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since July 21, 2025
 */
public interface IJPAClientDAO<T extends IPersistable> extends IJPAGenericDAO<T, Long> {

}
