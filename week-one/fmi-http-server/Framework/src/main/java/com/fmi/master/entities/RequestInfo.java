package com.fmi.master.entities;

import java.util.Objects;

public class RequestInfo {
    private String httpMethod;
    private String httpEndpoint;

    public RequestInfo(String httpMethod, String httpEndpoint) {
        this.httpMethod = httpMethod;
        this.httpEndpoint = httpEndpoint;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getHttpEndpoint() {
        return httpEndpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestInfo that = (RequestInfo) o;
        return Objects.equals(httpMethod, that.httpMethod) && Objects.equals(httpEndpoint, that.httpEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, httpEndpoint);
    }
}
