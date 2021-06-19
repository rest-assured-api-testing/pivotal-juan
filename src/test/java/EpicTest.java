import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Epic;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EpicTest extends BaseTest {

    @Test(groups = {"createProject","deleteProject"})
    public void getEpics() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("projects/{project_id}/epics")
                .withPathParams("project_id", projectEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","deleteProject"})
    public void createEpicStatusCode200() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("New Epic");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{project_id}/epics")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(epic)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        epicEndToEnd = apiResponse.getBody(Epic.class);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","deleteProject"})
    public void createEpicName() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("New Epic");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{project_id}/epics")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(epic)).build();
        epicEndToEnd = ApiManager.executeWithBody(apiRequest).getBody(Epic.class);
        Assert.assertEquals(epicEndToEnd.getName(), epic.getName());
    }

    @Test(groups = {"createProject","createEpic","deleteProject"})
    public void getEpic() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("projects/{project_id}/epics/{epic_id}")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withPathParams("epic_id", epicEndToEnd.getId().toString())
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createEpic","deleteProject"})
    public void updateEpic() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Epic update");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("projects/{project_id}/epics/{epic_id}")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withPathParams("epic_id", epicEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(epic)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        epicEndToEnd = apiResponse.getBody(Epic.class);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(epicEndToEnd.getName(), epic.getName());
    }

    @Test(groups = {"createProject","createEpic","deleteProject"})
    public void updateEpicName() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Epic update");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("projects/{project_id}/epics/{epic_id}")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withPathParams("epic_id", epicEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(epic)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        epicEndToEnd = apiResponse.getBody(Epic.class);
        Assert.assertEquals(epicEndToEnd.getName(), epic.getName());
    }

    @Test(groups = {"createProject","createEpic","deleteProject"})
    public void deleteAEpic() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("projects/{project_id}/epics/{epic_id}")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withPathParams("epic_id", epicEndToEnd.getId().toString())
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }
}
