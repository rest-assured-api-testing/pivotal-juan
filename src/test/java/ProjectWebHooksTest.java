import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.ProjectWebHooks;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProjectWebHooksTest extends BaseTest{

    @Test(groups = {"createProject","deleteProject"})
    public void getProjectWebHooksTest() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("projects/{project_id}/webhooks")
                .withPathParams("project_id", projectEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createWebHook","deleteProject"})
    public void getAProjectWebHookTest() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("projects/{project_id}/webhooks/{webhook_id}")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withPathParams("webhook_id", webHooksEndToEnd.getId().toString())
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }


    @Test(groups = {"createProject","createWebHook","deleteProject"})
    public void createProjectWebHooks() throws JsonProcessingException {
        ProjectWebHooks projectWebHooks = new ProjectWebHooks();
        projectWebHooks.setWebhook_url("https:///story/show/560");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{project_id}/webhooks")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(projectWebHooks)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        webHooksEndToEnd = apiResponse.getBody(ProjectWebHooks.class);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(webHooksEndToEnd.getWebhook_url(), projectWebHooks.getWebhook_url());
    }

    @Test(groups = {"createProject","createWebHook","deleteProject"})
    public void createProjectWebHooksStatusCode200() throws JsonProcessingException {
        ProjectWebHooks projectWebHooks = new ProjectWebHooks();
        projectWebHooks.setWebhook_url("https:///story/show/560");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{project_id}/webhooks")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(projectWebHooks)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createWebHook","deleteProject"})
    public void updateAProjectWebHook() throws JsonProcessingException {
        ProjectWebHooks projectWebHooks = new ProjectWebHooks();
        projectWebHooks.setWebhook_url("https:///story/show/555");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("projects/{project_id}/webhooks/{webhook_id}")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withPathParams("webhook_id", webHooksEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(projectWebHooks)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        webHooksEndToEnd = apiResponse.getBody(ProjectWebHooks.class);
        Assert.assertEquals(webHooksEndToEnd.getWebhook_url(), projectWebHooks.webhook_url);
    }

    @Test(groups = {"createProject","createWebHook","deleteProject"})
    public void updateAProjectWebHookStatusCode200() throws JsonProcessingException {
        ProjectWebHooks projectWebHooks = new ProjectWebHooks();
        projectWebHooks.setWebhook_url("https:///story/show/555");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("projects/{project_id}/webhooks/{webhook_id}")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withPathParams("webhook_id", webHooksEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(projectWebHooks)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createWebHook","deleteProject"})
    public void deleteAEpic() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("projects/{project_id}/webhooks/{webhook_id}")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withPathParams("webhook_id", webHooksEndToEnd.getId().toString())
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

}
