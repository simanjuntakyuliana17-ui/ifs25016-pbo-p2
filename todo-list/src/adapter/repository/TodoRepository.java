package adapter.repository;

import domain.entity.Todo;
import domain.repository.ITodoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TodoRepository implements ITodoRepository {
    private final List<Todo> data = new ArrayList<>();

    private int idCounter = 0;

    @Override
    public List<Todo> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Todo> findById(int id) {
        return data.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst();
    }

    @Override
    public Todo save(String title) {
        Todo todo = new Todo(nextId(), title, false);
        data.add(todo);
        return todo;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(todo -> todo.getId() == id);
    }

    @Override
    public void update(Todo todo) {
        // Entity bersifat mutable dan disimpan by-reference, sehingga perubahan
        // pada instance sudah otomatis tercermin di penyimpanan in-memory.
        // Method ini tetap ada agar kontrak port valid untuk implementasi lain.
    }

    /** Menghasilkan ID unik berikutnya. */
    private int nextId() {
        return ++idCounter;
    }
}
