package be.ucll.da.recommendation.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class RecommendedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID userId;
    private UUID ratedItem;
    private float rating;

    public RecommendedItem() {
    }

    public RecommendedItem(UUID userId, UUID ratedItem, float rating) {
        this.userId = userId;
        this.ratedItem = ratedItem;
        this.rating = rating;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getRatedItem() {
        return ratedItem;
    }

    public void setRatedItem(UUID ratedItem) {
        this.ratedItem = ratedItem;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendedItem that = (RecommendedItem) o;
        return Float.compare(that.getRating(), getRating()) == 0 &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getRatedItem(), that.getRatedItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getRatedItem(), getRating());
    }

    @Override
    public String toString() {
        return String.format("RecommendedItem{id=%s, userId=%s, ratedItem=%s, rating=%s}", id, userId, ratedItem, rating);
    }
}
