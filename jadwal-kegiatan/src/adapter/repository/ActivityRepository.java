package adapter.repository;

import domain.entity.Activity;
import domain.repository.IActivityRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActivityRepository implements IActivityRepository {
    private final List<Activity> data = new ArrayList<>();

    private int idCounter = 0;

    @Override
    public List<Activity> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Activity> findById(int id) {
        return data.stream()
                .filter(activity -> activity.getId() == id)
                .findFirst();
    }

    @Override
    public Activity save(String title, String day, String time) {
        Activity activity = new Activity(nextId(), title, day, time);
        data.add(activity);
        return activity;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(activity -> activity.getId() == id);
    }

    @Override
    public void update(Activity activity) {
        // Entity bersifat mutable dan disimpan by-reference, sehingga perubahan
        // pada instance sudah otomatis tercermin di penyimpanan in-memory.
        // Method ini tetap ada agar kontrak port valid untuk implementasi lain.
    }

    /** Menghasilkan ID unik berikutnya. */
    private int nextId() {
        return ++idCounter;
    }
}