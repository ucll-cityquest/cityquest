package be.ucll.da.recommendation.model;

import java.util.Objects;
import java.util.UUID;

public class Item {
    private UUID id;

    public Item(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public String toString() {
        return String.format("Item{id=%s}", id);
    }
}
