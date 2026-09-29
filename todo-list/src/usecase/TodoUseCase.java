package usecase;

import domain.entity.SortOption;
import domain.entity.Todo;
import domain.repository.ITodoRepository;
import java.util.List;
import java.util.Optional;

public class TodoUseCase {
    private final ITodoRepository todoRepository;

    public TodoUseCase(ITodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo addTodo(String title) {
        return todoRepository.save(title);
    }

    public boolean removeTodo(int id) {
        return todoRepository.deleteById(id);
    }

    /** Parameter bernilai null berarti field tersebut tidak diubah. */
    public boolean updateTodo(int id, String title, Boolean finished) {
        Optional<Todo> found = todoRepository.findById(id);
        if (found.isEmpty()) {
            return false;
        }

        Todo todo = found.get();
        if (title != null) {
            todo.setTitle(title);
        }

        if (finished != null) {
            todo.setFinished(finished);
        }

        todoRepository.update(todo);
        return true;
    }

    public List<Todo> searchTodos(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return todoRepository.findAll().stream()
                .filter(todo -> todo.getTitle().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    public List<Todo> sortTodos(SortOption option) {
        return todoRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }
}
