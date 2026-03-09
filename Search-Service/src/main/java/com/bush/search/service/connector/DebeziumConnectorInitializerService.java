package com.bush.search.service.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DebeziumConnectorInitializerService {
    private final ObjectMapper objectMapper;
    private final Environment environment;

    private final DebeziumConnectorService debeziumConnectorService;

    @Value("${spring.debezium.connectors.path}")
    private String connectorsPath;

    @EventListener(ApplicationReadyEvent.class)
    protected void initializeConnectorsAfterBoot() throws IOException {
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] jsonRequestResources = patternResolver.getResources(connectorsPath);
        for (Resource resource : jsonRequestResources) {
            String filename = Objects.requireNonNull(resource.getFilename());
            if (filename.endsWith(".json")) {
                createConnector(filename.substring(0, filename.indexOf('.')), resource);
            }
        }
    }

    private void createConnector(String connectorName, Resource jsonResource) throws IOException {
        if (!debeziumConnectorService.isConnectorExist(connectorName)) {
            debeziumConnectorService.registerConnector(getJsonStringConnectorConfiguration(jsonResource));
            log.info("Registered connector {}", connectorName);
        }
    }

    private String getJsonStringConnectorConfiguration(Resource jsonResource) throws IOException {
        String resourceContentString = jsonResource.getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> jsonConnectorConfigObjectMap = objectMapper.readValue(resourceContentString,
                new TypeReference<>() {});

        Map<String, String> connectorConfigurationMap = objectMapper.convertValue(jsonConnectorConfigObjectMap.get("config"),
                new TypeReference<>() {});
        return injectConnectorSecret(resourceContentString, connectorConfigurationMap.get("database.dbname"));
    }

    private String injectConnectorSecret(String targetString, String databaseName) {
        String username = Optional.ofNullable(environment.getProperty("spring.datasource.%s.username".formatted(databaseName)))
                .orElseThrow(IllegalArgumentException::new);
        String password = Optional.ofNullable(environment.getProperty("spring.datasource.%s.password".formatted(databaseName)))
                .orElseThrow(IllegalArgumentException::new);
        return targetString.replace("$POSTGRES_USERNAME", username).replace("$POSTGRES_PASSWORD", password);
    }
}
