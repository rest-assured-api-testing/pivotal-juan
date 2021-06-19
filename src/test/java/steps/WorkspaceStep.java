package steps;

import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import entities.Workspace;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;
import org.testng.Assert;

public class WorkspaceStep {
    private ApiRequest apiRequest = new ApiRequest();
    private ApiResponse apiResponse;
    Workspace workspace = new Workspace();

    private String userToken = "fae1f820733e34ef6385785b843dd339";
    private String baseUri = "https://www.pivotaltracker.com/services/v5";

    @Before
    public void createWorkspace() throws JsonProcessingException {
        Workspace workspaceTemp = new Workspace();
        workspaceTemp.setName("Workspace15");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setBaseUri(baseUri);
        apiRequest.setToken(userToken);
        apiRequest.addHeader("X-TrackerToken",userToken);
        apiRequest.setEndPoint("my/workspaces");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(workspaceTemp));
        workspace = ApiManager.executeWithBody(apiRequest).getBody(Workspace.class);
    }

    @Given("The method is {string}")
    public void theMethodIs (String method) {
        apiRequest.setBaseUri(baseUri);
        apiRequest.setToken(userToken);
        apiRequest.addHeader("X-TrackerToken",userToken);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute the endpoint {string} request")
    public void iExecuteTheEndpointRequest(String endpoint) {
        apiRequest.setEndPoint(endpoint);
        apiRequest.addPathParam("workspaceId", String.valueOf(workspace.getId()));
        apiResponse = ApiManager.execute(apiRequest);
    }

    @Then("status code should be {string}")
    public void statusCodeShouldBe (String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        apiResponse.getResponse().then().log().body();
    }

    @After
    public void deleteWorkspace() {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setBaseUri(baseUri);
        apiRequest.setToken(userToken);
        apiRequest.addHeader("X-TrackerToken",userToken);
        apiRequest.setEndPoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.valueOf("DELETE"));
        apiRequest.addPathParam("workspaceId",  String.valueOf(workspace.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

}
