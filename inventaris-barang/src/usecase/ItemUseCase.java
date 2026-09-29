package usecase;

import domain.entity.SortOption;
import domain.entity.Item;
import domain.repository.IItemRepository;
import java.util.List;
import java.util.Optional;

public class ItemUseCase {
    private final IItemRepository itemRepository;

    public ItemUseCase(IItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item addItem(String name, int quantity, String category) {
        if (quantity <= 0) {
            return null;
        }

        return itemRepository.save(name, quantity, category);
    }

    public boolean removeItem(int id) {
        return itemRepository.deleteById(id);
    }

    public boolean updateItem(int id, String name, Integer quantity, String category) {
        if (quantity != null && quantity <= 0) {
            return false;
        }

        Optional<Item> found = itemRepository.findById(id);
        if (found.isEmpty()) {
            return false;
        }

        Item item = found.get();
        if (name != null) {
            item.setName(name);
        }

        if (quantity != null) {
            item.setQuantity(quantity);
        }

        if (category != null) {
            item.setCategory(category);
        }

        itemRepository.update(item);
        return true;
    }

    public List<Item> searchItems(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return itemRepository.findAll().stream()
                .filter(item -> item.getName().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    public List<Item> sortItems(SortOption option) {
        return itemRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }
}
