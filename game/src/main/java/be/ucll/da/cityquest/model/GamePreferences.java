package be.ucll.da.cityquest.model;

import java.util.HashMap;
import java.util.UUID;

public class GamePreferences extends HashMap<UUID, Float> {
    public Float get(Game game) {
        return super.get(game.getId());
    }
}
