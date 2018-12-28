package be.ucll.da.cityquest.service;

import be.ucll.da.cityquest.controller.GameController;
import be.ucll.da.cityquest.model.GamePreferences;
import be.ucll.da.recommendation.model.RecommendedItem;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.naming.ServiceUnavailableException;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;


@Service
public class RecommendationService {
    private final static String SERVICE_ID = "recommendation";

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    public RecommendationService(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    private Optional<URI> serviceUri() {
        return discoveryClient.getInstances(SERVICE_ID)
                .stream()
                .map(ServiceInstance::getUri)
                .findAny();
    }

    public GamePreferences getRecomendations(UUID userId) throws ServiceUnavailableException {
        var uri = serviceUri()
                .map(s -> s.resolve("/recommend/" + userId))
                .orElseThrow(ServiceUnavailableException::new);

        return restTemplate
                .getForEntity(uri, GamePreferences.class)
                .getBody();
    }

    public RecommendedItem rateGame(UUID userId, UUID gameId, float rating) throws ServiceUnavailableException {
        var uri = serviceUri()
                .map(s -> s.resolve("/recommend"))
                .orElseThrow(ServiceUnavailableException::new);

        RecommendedItem recommendedItem = new RecommendedItem(userId, gameId, rating);

        return restTemplate
                .postForEntity(uri, recommendedItem, RecommendedItem.class)
                .getBody();
    }

}
