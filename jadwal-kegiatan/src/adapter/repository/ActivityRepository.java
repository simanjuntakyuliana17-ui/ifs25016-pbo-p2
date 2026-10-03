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
        List<Activity> copies = new ArrayList<>();
        for (Activity activity : data) {
            copies.add(copyOf(activity));
        }

        return copies;
    }

    @Override
    public Optional<Activity> findById(int id) {
        return data.stream()
                .filter(activity -> activity.getId() == id)
                .findFirst()
                .map(this::copyOf);
    }

    @Override
    public Activity save(String title, String day, String time) {
        Activity activity = new Activity(nextId(), title, day, time);
        data.add(activity);
        return copyOf(activity);
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(activity -> activity.getId() == id);
    }

    @Override
    public void update(Activity activity) {
        // findById/findAll mengembalikan salinan, jadi perubahan baru tersimpan
        // setelah objek yang tersimpan diganti dengan salinan dari parameter.
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == activity.getId()) {
                data.set(i, copyOf(activity));
                return;
            }
        }
    }

    /** Defensive copy: entity asli tidak pernah keluar dari repository. */
    private Activity copyOf(Activity activity) {
        return new Activity(activity.getId(), activity.getTitle(), activity.getDay(), activity.getTime());
    }

    /** Menghasilkan ID unik berikutnya. */
    private int nextId() {
        return ++idCounter;
    }
}
