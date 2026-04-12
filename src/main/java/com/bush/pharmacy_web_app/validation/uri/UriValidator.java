package com.bush.pharmacy_web_app.validation.uri;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Component
public class UriValidator {
    public boolean isValidRelativePath(String uri) {
        if (uri.isBlank()) {
            return false;
        }
        if (uri.contains("\r") || uri.contains("\n") || uri.contains("\0")) {
            return false;
        }
        try {
            URI uriObject = new URI(uri);
            if (uriObject.getScheme() == null && uriObject.getHost() == null) {
                String path = uriObject.getPath();
                if (Objects.nonNull(path)) {
                    return !path.startsWith("//");
                }
                return true;
            }
            return false;
        } catch (URISyntaxException | IllegalArgumentException e) {
            return false;
        }
    }
}
