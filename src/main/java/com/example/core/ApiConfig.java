package com.example.core;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class ApiConfig {
    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";

    private static final RequestSpecification REQUEST_SPEC = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setContentType(ContentType.JSON)
            .addFilter(new RequestLoggingFilter(LogDetail.URI))
            .addFilter(new RequestLoggingFilter(LogDetail.METHOD))
            .addFilter(new RequestLoggingFilter(LogDetail.PARAMS))
            .addFilter(new ResponseLoggingFilter(LogDetail.STATUS))
            .build();

    private ApiConfig() {
    }

    public static RequestSpecification requestSpec() {
        return REQUEST_SPEC;
    }
}
