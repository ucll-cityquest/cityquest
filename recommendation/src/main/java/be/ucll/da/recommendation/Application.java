package be.ucll.da.recommendation;

import be.ucll.da.recommendation.model.Item;
import be.ucll.da.recommendation.model.RecommendedItem;
import be.ucll.da.recommendation.model.User;
import be.ucll.da.recommendation.repository.RecommendedItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private static UUID romeinen = UUID.fromString("b4357989-e3b1-42f8-a325-c2591e5c6ad8");
    private static UUID toverdrank = UUID.fromString("b4357989-e3b1-42f8-a325-c2591e5c6ad9");
    private static UUID everzwijnen = UUID.fromString("b4357989-e3b1-42f8-a325-c2591e5c6ad7");

    @Bean
    public CommandLineRunner demo(RecommendedItemRepository repository) {
        return (args) -> {
            HashMap<User, Map<Item, Float>> userMap = new HashMap<>();

            HashMap<Item, Float> asterixPreferences = new HashMap<>();
            asterixPreferences.put(new Item(toverdrank), 4.8f);
            asterixPreferences.put(new Item(romeinen), 1.2f);
            asterixPreferences.put(new Item(everzwijnen), 4.6f);
            userMap.put(new User("asterix@ucll.be"), asterixPreferences);

            HashMap<Item, Float> obelixPreferences = new HashMap<>();
            obelixPreferences.put(new Item(toverdrank), 4.4f);
            obelixPreferences.put(new Item(romeinen), 3.2f);
            obelixPreferences.put(new Item(everzwijnen), 5f);
            userMap.put(new User("obelix@ucll.be"), obelixPreferences);

            HashMap<Item, Float> panoramixPreferences = new HashMap<>();
            panoramixPreferences.put(new Item(toverdrank), 4.2f);
            panoramixPreferences.put(new Item(romeinen), 2.2f);
            panoramixPreferences.put(new Item(everzwijnen), 4.1f);
            userMap.put(new User("idefix@ucll.be"), panoramixPreferences);

            HashMap<Item, Float> ronaldPreferences = new HashMap<>();
            ronaldPreferences.put(new Item(toverdrank), 4.9f);
            userMap.put(new User("ronald.dehuysser@ucll.be"), ronaldPreferences);

            saveUserMapToRepository(repository, userMap);
        };
    }

    private void saveUserMapToRepository(RecommendedItemRepository repository, HashMap<User, Map<Item, Float>> userMap) {
        List<RecommendedItem> recommendedItems = userMap.entrySet().stream()
                .flatMap(this::toRecommendedItems)
                .collect(Collectors.toList());

        repository.saveAll(recommendedItems);
    }

    private Stream<RecommendedItem> toRecommendedItems(Map.Entry<User, Map<Item, Float>> entry) {
        final User user = entry.getKey();
        final Map<Item, Float> ratingMap = entry.getValue();
        return entry
                .getValue()
                .keySet()
                .stream()
                .map(item -> itemToRecommendedItem(user, item, ratingMap.get(item)));
    }

    private RecommendedItem itemToRecommendedItem(User user, Item item, float rating) {
        RecommendedItem aRecommendedItem = new RecommendedItem();
        aRecommendedItem.setEmailAddress(user.toString());
        aRecommendedItem.setRatedItem(UUID.fromString(item.toString()));
        aRecommendedItem.setRating(rating);
        return aRecommendedItem;
    }
}
