package ru.schoolbolt.msbackend.server;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;

public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(
            final ContainerRequestContext containerRequestContext,
            final ContainerResponseContext containerResponseContext
    ) {
        final MultivaluedMap<String, Object> headers = containerResponseContext.getHeaders();
        headers.add(
                "Access-Control-Allow-Origin",
                "*"
        );
        headers.add(
                "Access-Control-Allow-Credentials",
                "true"
        );
        headers.add(
                "Access-Control-Allow-Headers",
                "origin, content-type, accept"
        );
        headers.add(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD"
        );
    }
}
