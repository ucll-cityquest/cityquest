package be.ucll.da.recommendation.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Map.entry;

public class SlopeOneTest {
    private final static UUID ROMEINEN = UUID.randomUUID();
    private final static UUID TOVERDRANK = UUID.randomUUID();
    private final static UUID EVERZWIJNEN = UUID.randomUUID();
    private final static UUID ASTERIX = UUID.randomUUID();
    private final static UUID OBELIX = UUID.randomUUID();
    private final static UUID PANORAMIX = UUID.randomUUID();

    @Test
    public void doesSlopeOneDoWhatIExpectItToDo() {
        var userPreferences = createUserPreferencesMap();

        var slopeOne = new SlopeOne(userPreferences);

        var kakoPhonixPreferences = new HashMap<Item, Float>();
        kakoPhonixPreferences.put(new Item(TOVERDRANK), 4.5f);

        var predict = slopeOne.predict(kakoPhonixPreferences);
        System.out.println(predict);
    }

    @Test
    public void newUserDidNotRecommendAnything() {
        var userPreferences = createUserPreferencesMap();

        var slopeOne = new SlopeOne(userPreferences);

        var kakoPhonixPreferences = new HashMap<Item, Float>();
        kakoPhonixPreferences.put(new Item(TOVERDRANK), 4.1f);
        var predict = slopeOne.predict(kakoPhonixPreferences);
        System.out.println(predict);
    }

    private Map<User, Map<Item, Float>> createUserPreferencesMap() {
        var userMap = new HashMap<User, Map<Item, Float>>();

        var asterixPreferences = createPreferences(4.8f, 1.2f, 4.6f);
        userMap.put(new User(ASTERIX), asterixPreferences);

        var obelixPreferences = createPreferences(4.4f, 3.2f, 5f);
        userMap.put(new User(OBELIX), obelixPreferences);

        var panoramixPreferences = createPreferences(4.2f, 2.2f, 4.1f);
        userMap.put(new User(PANORAMIX), panoramixPreferences);

        return userMap;
    }


    private Map<Item, Float> createPreferences(float toverdrank, float romeinen, float everzwijnen) {
        return Map.ofEntries(
                entry(new Item(TOVERDRANK), toverdrank),
                entry(new Item(ROMEINEN), romeinen),
                entry(new Item(EVERZWIJNEN), everzwijnen)
        );
    }

}
