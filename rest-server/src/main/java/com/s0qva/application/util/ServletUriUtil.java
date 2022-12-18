package com.s0qva.application.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@UtilityClass
public class ServletUriUtil {

    public URI getUriFromCurrentRequest(String path, Object... parameters) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(parameters)
                .toUri();
    }

    public URI getUri(String uriPath, Object... parameters) {
        return ServletUriComponentsBuilder.fromUriString(uriPath)
                .buildAndExpand(parameters)
                .toUri();
    }
}
