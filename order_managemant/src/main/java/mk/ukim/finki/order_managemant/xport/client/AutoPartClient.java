package mk.ukim.finki.order_managemant.xport.client;

import mk.ukim.finki.order_managemant.domain.valueObjects.AutoPart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

@Service
public class AutoPartClient {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    public AutoPartClient(@Value("${app.product-catalog.url}")String serverUrl) {
        this.serverUrl = serverUrl;
        this.restTemplate = new RestTemplate();
        var requestFactory = new SimpleClientHttpRequestFactory();
        this.restTemplate.setRequestFactory(requestFactory);

    }

    private UriComponentsBuilder uri() {
        return UriComponentsBuilder.fromUriString(this.serverUrl);
    }

    public List<AutoPart> findAll() {
        try {
            return restTemplate.exchange(uri().path("/api/autoPart").build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AutoPart>>() {

                }).getBody();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }


}
