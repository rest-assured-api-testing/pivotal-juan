package steps;

import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Epic;
import entities.Project;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;
import org.testng.Assert;

public class EpicStep {
    private ApiRequest apiRequest = new ApiRequest();
    private ApiResponse apiResponse;
    Project project = new Project();
    Epic epic = new Epic();

    private String userToken = "fae1f820733e34ef6385785b843dd339";
    private String baseUri = "https://www.pivotaltracker.com/services/v5";

    @Before
    public void createEpic() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Project19");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setBaseUri(baseUri);
        apiRequest.setToken(userToken);
        apiRequest.addHeader("X-TrackerToken",userToken);
        apiRequest.setEndPoint("projects");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(projectTemp));
        project = ApiManager.executeWithBody(apiRequest).getBody(Project.class);
        ApiRequest apiRequest2 = new ApiRequest();
        Epic epicTemp = new Epic();
        epicTemp.setName("Epic33");
        apiRequest2.setBaseUri(baseUri);
        apiRequest2.setToken(userToken);
        apiRequest2.addHeader("X-TrackerToken",userToken);
        apiRequest2.setEndPoint("projects/{projectId}/epics");
        apiRequest2.setMethod(ApiMethod.valueOf("POST"));
        apiRequest2.addPathParam("projectId", project.getId().toString());
        apiRequest2.setBody(new ObjectMapper().writeValueAsString(epicTemp));
        epic = ApiManager.executeWithBody(apiRequest2).getBody(Epic.class);

        System.out.println("PROJECT "+project.getName());
        System.out.println("EPIC "+epic.getName());
    }

    @Given("The method for epic is {string}")
    public void theMethodForEpicIs (String method) {
        apiRequest.setBaseUri(baseUri);
        apiRequest.setToken(userToken);
        apiRequest.addHeader("X-TrackerToken",userToken);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("find epic {string} request")
    public void findEpicRequest(String endpoint) {
        apiRequest.setEndPoint(endpoint);
        apiRequest.addPathParam("projectId", project.getId().toString());
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        apiResponse = ApiManager.execute(apiRequest);
    }

    @Then("the result is {string}")
    public void theResultIs (String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        apiResponse.getResponse().then().log().body();
    }

    @After
    public void deleteEpic() {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setBaseUri(baseUri);
        apiRequest.setToken(userToken);
        apiRequest.addHeader("X-TrackerToken",userToken);
        apiRequest.setEndPoint("projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.valueOf("DELETE"));
        apiRequest.addPathParam("projectId",  project.getId().toString());
        apiRequest.addPathParam("epicId",  String.valueOf(epic.getId()));
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
