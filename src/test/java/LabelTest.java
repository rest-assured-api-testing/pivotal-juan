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
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("projects/{project_id}/labels")
                .withPathParams("project_id", projectEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void createProjectLabelName() throws JsonProcessingException {
        Label label = new Label();
        label.setName("label");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{project_id}/labels")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(label)).build();
        labelEndToEnd = ApiManager.executeWithBody(apiRequest).getBody(Label.class);
        Assert.assertEquals(labelEndToEnd.getName(), label.getName());
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void createProjectLabelStatusCode200() throws JsonProcessingException {
        Label label = new Label();
        label.setName("label");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{project_id}/labels")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(label)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void getAEpicTest() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("projects/{project_id}/labels/{label_id}")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withPathParams("label_id", labelEndToEnd.getId().toString())
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void updateAEpic() throws JsonProcessingException {
        Label label = new Label();
        label.setName("label update");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("projects/{project_id}/labels/{label_id}")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withPathParams("label_id", labelEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(label)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        labelEndToEnd = apiResponse.getBody(Label.class);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void updateAEpicName() throws JsonProcessingException {
        Label label = new Label();
        label.setName("label update");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("projects/{project_id}/labels/{label_id}")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withPathParams("label_id", labelEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(label)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        labelEndToEnd = apiResponse.getBody(Label.class);
        Assert.assertEquals(labelEndToEnd.getName(), label.getName());
    }

    @Test(groups = {"createProject","createLabel","deleteProject"})
    public void deleteAEpicTest() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("projects/{project_id}/labels/{label_id}")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withPathParams("label_id", labelEndToEnd.getId().toString())
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }
}
