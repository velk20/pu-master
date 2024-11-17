package com.fmi.master.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestInfo {
    private String httpMethod;
    private String httpEndpoint;
    private String httpBody;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> pathVariables = new HashMap<>();


    public RequestInfo() {
        this.httpMethod = "";
        this.httpEndpoint = "";
    }

    public RequestInfo(String httpMethod, String httpEndpoint) {
        this.httpMethod = httpMethod;
        this.httpEndpoint = httpEndpoint;
    }

    public RequestInfo(String httpMethod, String httpEndpoint, String httpBody) {
        this.httpMethod = httpMethod;
        this.httpEndpoint = httpEndpoint;
        this.httpBody = httpBody;
    }

    public boolean isEmpty() {
        return httpMethod.isEmpty() || httpEndpoint.isEmpty();
    }

    public RequestInfo setPathVariables(Map<String, String> pathVariables) {
        this.pathVariables = pathVariables;
        return this;
    }

    public boolean isProcessable(String method, String endpoint) {
        if (!this.httpMethod.equals(method)) {
            return false;
        }

        return isTemplateEndpointMatchRequestEndpoint(endpoint);
    }
    public boolean isTemplateEndpointMatchRequestEndpoint(String requestEndpoint) {
        String[] templateEndpointPartCollection = this.getHttpEndpoint().split("/");
        String[] requestEndpointPartCollection = requestEndpoint.split("/");

        if (templateEndpointPartCollection.length != requestEndpointPartCollection.length) {
            return false;
        }

        for (int i = 0; i < templateEndpointPartCollection.length; i++) {
            if (isUrlPartDynamic(templateEndpointPartCollection[i])){
                String pathVariableName = extractUrlVariable(templateEndpointPartCollection[i]);
                String pathVariableValue = requestEndpointPartCollection[i];
                pathVariables.put(pathVariableName, pathVariableValue);
                continue;
            }
            if (!templateEndpointPartCollection[i].equals(requestEndpointPartCollection[i])) {
                return false;
            }



        }

        return true;
    }

    public Map<String, String> getPathVariables() {
        return pathVariables;
    }

    private String extractUrlVariable(String template) {
        return template.substring(1, template.length() - 1);
    }
    private boolean isUrlPartDynamic(String urlPart)    {
        return urlPart.startsWith("{")
                && urlPart.endsWith("}");
    }
    public int getContentLength(){
        String value = this.getHeader("Content-Length");
        if (Objects.isNull(value)) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    public  boolean hasContent(){
        return this.getContentLength() > 0;
    }

    public boolean hasMethodAndEndpoint() {
        return !this.getHttpEndpoint().isEmpty() && !this.getHttpMethod().isEmpty();
    }

    public void setHeader(String key,String value) {
        this.headers.put(key, value);
    }

    public RequestInfo setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public RequestInfo setHttpEndpoint(String httpEndpoint) {
        this.httpEndpoint = httpEndpoint;
        return this;
    }

    public RequestInfo setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public boolean hasHeader(String key) {
        return this.headers.containsKey(key);
    }

    public  String getHeader(String key) {
        return this.headers.get(key);
    }

    public String getHttpBody() {
        return httpBody;
    }

    public RequestInfo setHttpBody(String httpBody) {
        this.httpBody = httpBody;
        return this;
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
