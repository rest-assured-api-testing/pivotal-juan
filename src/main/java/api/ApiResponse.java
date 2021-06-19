package api;

import io.restassured.response.Response;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ApiResponse {
    private Response response;

    public ApiResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public int getStatusCode() {
        return this.response.getStatusCode();
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public <T> T getBody(Class<T> cls) {
        return response.getBody().as(cls);
    }

    public <T> List<T> getBodyList(Class<T> cls) {
        return response.getBody().jsonPath().getList("",cls);
    }

    public void validateSchema(String schema) {
        response.then().log().body().assertThat().body(matchesJsonSchemaInClasspath(schema));
    }
}
