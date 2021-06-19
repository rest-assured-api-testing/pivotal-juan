package steps;

import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entities.Label;
import entities.Project;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;
import org.testng.Assert;

public class LabelStep {
    private ApiRequest apiRequest = new ApiRequest();
    private ApiResponse apiResponse;
    Project project = new Project();
    Label label = new Label();

    private String userToken = "fae1f820733e34ef6385785b843dd339";
    private String baseUri = "https://www.pivotaltracker.com/services/v5";

    @Before
    public void createLabel() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Project29");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setBaseUri(baseUri);
        apiRequest.setToken(userToken);
        apiRequest.addHeader("X-TrackerToken",userToken);
        apiRequest.setEndPoint("projects");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(projectTemp));
        project = ApiManager.executeWithBody(apiRequest).getBody(Project.class);
        ApiRequest apiRequest2 = new ApiRequest();
        Label labelTemp = new Label();
        labelTemp.setName("Label43");
        apiRequest2.setBaseUri(baseUri);
        apiRequest2.setToken(userToken);
        apiRequest2.addHeader("X-TrackerToken",userToken);
        apiRequest2.setEndPoint("projects/{projectId}/labels");
        apiRequest2.setMethod(ApiMethod.valueOf("POST"));
        apiRequest2.addPathParam("projectId", project.getId().toString());
        apiRequest2.setBody(new ObjectMapper().writeValueAsString(labelTemp));
        label = ApiManager.executeWithBody(apiRequest2).getBody(Label.class);
    }

    @Given("The method for label is {string}")
    public void theMethodForLabelIs (String method) {
        apiRequest.setBaseUri(baseUri);
        apiRequest.setToken(userToken);
        apiRequest.addHeader("X-TrackerToken",userToken);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("find label {string} request")
    public void findLabelRequest(String endpoint) {
        apiRequest.setEndPoint(endpoint);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addPathParam("labelId", String.valueOf(label.getId()));
        apiResponse = ApiManager.execute(apiRequest);
    }

    @Then("the label result is {string}")
    public void theLabelResultIs (String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        apiResponse.getResponse().then().log().body();
    }

    @After
    public void deleteLabel() {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setBaseUri(baseUri);
        apiRequest.setToken(userToken);
        apiRequest.addHeader("X-TrackerToken",userToken);
        apiRequest.setEndPoint("projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.valueOf("DELETE"));
        apiRequest.addPathParam("projectId",  project.getId().toString());
        apiRequest.addPathParam("labelId",  String.valueOf(label.getId()));
        apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
        ApiRequest apiRequest2 = new ApiRequest();
        apiRequest2.clearPathParams();
        apiRequest2.setBaseUri(baseUri);
        apiRequest2.setToken(userToken);
        apiRequest2.addHeader("X-TrackerToken",userToken);
        apiRequest2.setEndPoint("projects/{projectId}");
        apiRequest2.setMethod(ApiMethod.valueOf("DELETE"));
        apiRequest2.addPathParam("projectId",  project.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest2);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }
}
