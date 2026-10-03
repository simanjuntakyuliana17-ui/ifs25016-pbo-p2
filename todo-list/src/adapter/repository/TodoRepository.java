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
        List<Todo> copies = new ArrayList<>();
        for (Todo todo : data) {
            copies.add(copyOf(todo));
        }

        return copies;
    }

    @Override
    public Optional<Todo> findById(int id) {
        return data.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .map(this::copyOf);
    }

    @Override
    public Todo save(String title) {
        Todo todo = new Todo(nextId(), title, false);
        data.add(todo);
        return copyOf(todo);
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(todo -> todo.getId() == id);
    }

    @Override
    public void update(Todo todo) {
        // findById/findAll mengembalikan salinan, jadi perubahan baru tersimpan
        // setelah objek yang tersimpan diganti dengan salinan dari parameter.
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == todo.getId()) {
                data.set(i, copyOf(todo));
                return;
            }
        }
    }

    /** Defensive copy: entity asli tidak pernah keluar dari repository. */
    private Todo copyOf(Todo todo) {
        return new Todo(todo.getId(), todo.getTitle(), todo.isFinished());
    }

    /** Menghasilkan ID unik berikutnya. */
    private int nextId() {
        return ++idCounter;
    }
}
