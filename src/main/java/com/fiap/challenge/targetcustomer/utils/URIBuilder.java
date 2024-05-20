package com.fiap.challenge.targetcustomer.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class URIBuilder {

    public static URI createFromId(Long id) {
        var path = "/" + id;
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .build()
                .toUri();
    }
}
