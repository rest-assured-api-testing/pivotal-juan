package api;

public class ApiRequestBuilder implements IApiRequestBuilder{

    ApiRequest apiRequest = new ApiRequest();

    public ApiRequestBuilder() {
        this.apiRequest = new ApiRequest();
    }

    public ApiRequestBuilder withBaseUri(String baseUri) {
        this.apiRequest.setBaseUri(baseUri);
        return this;
    }

    public ApiRequestBuilder withEndpoint (String endPoint) {
        this.apiRequest.setEndPoint(endPoint);
        return this;
    }

    public ApiRequestBuilder withMethod (ApiMethod method) {
        this.apiRequest.setMethod(method);
        return this;
    }

    public ApiRequestBuilder withBody(String body) {
        this.apiRequest.setBody(body);
        return this;
    }

    public ApiRequestBuilder withToken(String token) {
        this.apiRequest.setToken(token);
        return this;
    }

    public ApiRequestBuilder withHeaders(final String header, final String value) {
        this.apiRequest.addHeader(header, value);
        return this;
    }

    public ApiRequestBuilder withPathParams(final String param, final String value) {
        this.apiRequest.addPathParam(param, value);
        return this;
    }

    public ApiRequestBuilder withQueryParams(final String param, final String value) {
        this.apiRequest.addQueryParam(param, value);
        return this;
    }

    public ApiRequest build() {
        return apiRequest;
    }
}
