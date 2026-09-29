package usecase;

import domain.entity.SortOption;
import domain.entity.Activity;
import domain.repository.IActivityRepository;
import java.util.List;
import java.util.Optional;

public class ActivityUseCase {
    private final IActivityRepository activityRepository;

    public ActivityUseCase(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> getAllActivitys() {
        return activityRepository.findAll();
    }

    public Activity addActivity(String title, String day, String time) {
        return activityRepository.save(title, day, time);
    }

    public boolean removeActivity(int id) {
        return activityRepository.deleteById(id);
    }

    public boolean updateActivity(int id, String title, String day, String time) {
        Optional<Activity> found = activityRepository.findById(id);
        if (found.isEmpty()) {
            return false;
        }

        Activity activity = found.get();
        if (title != null) {
            activity.setTitle(title);
        }

        if (day != null) {
            activity.setDay(day);
        }

        if (time != null) {
            activity.setTime(time);
        }

        activityRepository.update(activity);
        return true;
    }

    public List<Activity> searchActivitys(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return activityRepository.findAll().stream()
                .filter(activity -> activity.getTitle().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    public List<Activity> sortActivitys(SortOption option) {
        return activityRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }
}