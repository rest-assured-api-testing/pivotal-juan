import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Label;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LabelTest extends BaseTest{

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void getProjectLabels() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("projects/{projectId}/labels")
                .withPathParams("projectId", projectEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void getProjectLabelsWithWrongId() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("projects/{projectId}/labels")
                .withPathParams("projectId", String.valueOf(projectEndToEnd.getAccount_id())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void createProjectLabelName() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Label label = new Label();
        label.setName("label");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{projectId}/labels")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(label)).build();
        labelEndToEnd = ApiManager.executeWithBody(apiRequest).getBody(Label.class);
        Assert.assertEquals(labelEndToEnd.getName(), label.getName());
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void createProjectLabelStatusCode200() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Label label = new Label();
        label.setName("label");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{projectId}/labels")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(label)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void createProjectLabelWithEmptyBody() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Label label = new Label();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{projectId}/labels")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(label)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
    }


    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void getAEpicTest() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("projects/{projectId}/labels/{labelId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("labelId", labelEndToEnd.getId().toString())
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void getAEpicWithWrongID() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("projects/{projectId}/labels/{labelId}")
                .withPathParams("projectId", String.valueOf(projectEndToEnd.getAccount_id()))
                .withPathParams("labelId", labelEndToEnd.getId().toString())
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void updateEpic() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Label label = new Label();
        label.setName("label update");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("projects/{projectId}/labels/{labelId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("labelId", labelEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(label)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        labelEndToEnd = apiResponse.getBody(Label.class);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void updateEpicName() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Label label = new Label();
        label.setName("label update");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("projects/{projectId}/labels/{labelId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("labelId", labelEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(label)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        labelEndToEnd = apiResponse.getBody(Label.class);
        Assert.assertEquals(labelEndToEnd.getName(), label.getName());
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void updateEpicWithWrongID() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Label label = new Label();
        label.setName("label update");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("projects/{projectId}/labels/{labelId}")
                .withPathParams("projectId",String.valueOf(projectEndToEnd.getAccount_id()))
                .withPathParams("labelId", labelEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(label)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void updateEpicWithEmptyBody() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Label label = new Label();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("projects/{projectId}/labels/{labelId}")
                .withPathParams("projectId",projectEndToEnd.getId().toString())
                .withPathParams("labelId", labelEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(label)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_BAD_REQUEST);
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void deleteEpic() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("projects/{projectId}/labels/{labelId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("labelId", labelEndToEnd.getId().toString())
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void deleteEpicWithWrongId() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("projects/{projectId}/labels/{labelId}")
                .withPathParams("projectId", String.valueOf(projectEndToEnd.getAccount_id()))
                .withPathParams("labelId", labelEndToEnd.getId().toString())
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }
}
