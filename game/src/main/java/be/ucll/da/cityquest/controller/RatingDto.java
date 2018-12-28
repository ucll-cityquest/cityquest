package be.ucll.da.cityquest.controller;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class RatingDto {
    @NotNull
    private UUID gameId;

    @NotNull
    private UUID userId;

    @Min(0)
    @Max(5)
    private float rating;

    public RatingDto() {
    }

    public RatingDto(UUID gameId, UUID userId, float rating) {
        this.gameId = gameId;
        this.userId = userId;
        this.rating = rating;
    }

    public UUID getGameId() {
        return gameId;
    }

    private void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getUserId() {
        return userId;
    }

    private void setUserId(UUID userId) {
        this.userId = userId;
    }

    public float getRating() {
        return rating;
    }

    private void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingDto ratingDto = (RatingDto) o;
        return Float.compare(ratingDto.getRating(), getRating()) == 0 &&
                Objects.equals(getGameId(), ratingDto.getGameId()) &&
                Objects.equals(getUserId(), ratingDto.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameId(), getUserId(), getRating());
    }

    @Override
    public String toString() {
        return String.format("RatingDto{gameId=%s, userId=%s, rating=%s}", gameId, userId, rating);
    }
}
