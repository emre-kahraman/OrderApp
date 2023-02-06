package repository;

import java.util.List;
import java.util.Optional;

public interface ListRepository<T> {

    boolean save(T model);

    boolean save(List<T> models);

    Optional<T> findById(Long id);

    List<T> findAll();
}
