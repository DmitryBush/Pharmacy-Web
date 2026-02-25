package com.bush.search.service.connector;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class DebeziumConnectorService {
    @Value("${spring.debezium.url}")
    private String debeziumUrl;

    public boolean isConnectorExist(String connectorName) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI url = UriComponentsBuilder.fromUriString(debeziumUrl)
                    .path("/")
                    .pathSegment("connectors")
                    .pathSegment(connectorName)
                    .build()
                    .toUri();

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    public void registerConnector(String jsonConnectorConfiguration) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI url = UriComponentsBuilder.fromUriString(debeziumUrl)
                    .path("/")
                    .pathSegment("connectors")
                    .build()
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(jsonConnectorConfiguration, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new HttpClientErrorException(response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            log.error("An error has occurred with status {}", e.getStatusCode());
            throw e;
        } catch (RestClientException e) {
            log.error("An error has occurred while generating request");
            throw e;
        }
    }
}
