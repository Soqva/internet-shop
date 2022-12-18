package com.s0qva.application.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@UtilityClass
public class ServletUriUtil {

    public URI getUriFromCurrentRequest(String path, Object... parameter) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(parameter)
                .toUri();
    }
}
