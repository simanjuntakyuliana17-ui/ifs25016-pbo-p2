import adapter.presenter.TodoPresenter;
import adapter.repository.TodoRepository;
import domain.repository.ITodoRepository;
import framework.view.TodoView;
import usecase.TodoUseCase;

/**
 * Titik masuk aplikasi Todo List (Composition Root).
 * Semua dependency antar layer disusun di sini — satu-satunya tempat
 * yang mengetahui implementasi konkret dari setiap interface.
 */
public class App {
    public static void main(String[] args) {
        ITodoRepository todoRepository = new TodoRepository();
        TodoUseCase todoUseCase = new TodoUseCase(todoRepository);
        TodoPresenter todoPresenter = new TodoPresenter();
        TodoView todoView = new TodoView(todoUseCase, todoPresenter);

        todoView.show();
    }
}
