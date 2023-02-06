package repository;

import model.Invoice;

import java.util.Collection;
import java.util.Set;

public interface HashMapRepository<ID, T> {

    boolean save(ID id, T value);

    boolean save(ID id, Set<T> values);

    Set<T> findById(ID id);

    Collection<Set<Invoice>> findAll();
}
