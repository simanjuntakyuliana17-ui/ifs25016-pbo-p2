package domain.repository;

import domain.entity.Todo;
import java.util.List;
import java.util.Optional;

public interface ITodoRepository {
    List<Todo> findAll();

    Optional<Todo> findById(int id);

    Todo save(String title);

    boolean deleteById(int id);

    void update(Todo todo);
}
