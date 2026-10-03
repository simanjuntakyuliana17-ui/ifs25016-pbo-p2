package adapter.repository;

import domain.entity.Item;
import domain.repository.IItemRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemRepository implements IItemRepository {
    private final List<Item> data = new ArrayList<>();

    private int idCounter = 0;

    @Override
    public List<Item> findAll() {
        List<Item> copies = new ArrayList<>();
        for (Item item : data) {
            copies.add(copyOf(item));
        }

        return copies;
    }

    @Override
    public Optional<Item> findById(int id) {
        return data.stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .map(this::copyOf);
    }

    @Override
    public Item save(String name, int quantity, String category) {
        Item item = new Item(nextId(), name, quantity, category);
        data.add(item);
        return copyOf(item);
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(item -> item.getId() == id);
    }

    @Override
    public void update(Item item) {
        // findById/findAll mengembalikan salinan, jadi perubahan baru tersimpan
        // setelah objek yang tersimpan diganti dengan salinan dari parameter.
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == item.getId()) {
                data.set(i, copyOf(item));
                return;
            }
        }
    }

    /** Defensive copy: entity asli tidak pernah keluar dari repository. */
    private Item copyOf(Item item) {
        return new Item(item.getId(), item.getName(), item.getQuantity(), item.getCategory());
    }

    private int nextId() {
        return ++idCounter;
    }
}
