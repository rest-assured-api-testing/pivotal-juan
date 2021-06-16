package api;

import io.restassured.http.Header;
import io.restassured.http.Headers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiRequest {
    private String baseUri;
    private String endPoint;
    private ApiMethod method;
    private String body;
    private String token;
    private List<Header> headers;
    private Map<String,String>  queryParams;
    private Map<String,String>  pathParams;

    public ApiRequest(){
        headers = new ArrayList<>();
        queryParams = new HashMap<>();
        pathParams = new HashMap<>();
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public ApiMethod getMethod() {
        return method;
    }

    public void setMethod(ApiMethod method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Headers getHeaders() {
        return new Headers(headers);
    }

    public void addHeader(final String header, final String value) {
        this.headers.add(new Header(header,value));
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void addQueryParam(final String param, final String value) {
        this.queryParams.put(param,value);
    }

    public Map<String, String> getPathParams() {
        return pathParams;
    }

    public void addPathParam(final String param, final String value) {
        this.pathParams.put(param,value);
    }
}
