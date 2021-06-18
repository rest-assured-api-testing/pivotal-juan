import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import entities.Workspace;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class WoskspaceTest {
    ApiRequest apiRequest = new ApiRequest();
    ApiRequestBuilder apiRequestBuilder = new ApiRequestBuilder();
    Workspace workspaceEndToEnd = new Workspace();

    @BeforeTest
    public void setupConfiguration(){
        apiRequest = apiRequestBuilder
                .withBaseUri("https://www.pivotaltracker.com/services/v5")
                .withHeaders("X-TrackerToken", "fae1f820733e34ef6385785b843dd339")
                .withToken("fae1f820733e34ef6385785b843dd339")
                .build();
    }

    @Test
    public void getAllWorkspace() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/my/workspaces").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_OK);
    }

    @Test
    public void getWorkspaceWithStatusCode200(){
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/my/workspaces/{workspace_id}")
                .withPathParams("workspace_id", "876147").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void getWorkspaceWithName(){
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/my/workspaces/{workspace_id}")
                .withPathParams("workspace_id", "876147").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Workspace workspace = apiResponse.getBody(Workspace.class);
        Assert.assertEquals(workspace.getName(),"A new workspace");
    }

    @Test
    public void addWorkspace() throws JsonProcessingException {
        Workspace workspace = new Workspace();
        workspace.setName("Workspace3");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/my/workspaces")
                .withBody(new ObjectMapper().writeValueAsString(workspace)).build();
        Workspace workspaceResponse = ApiManager.executeWithBody(apiRequest).getBody(Workspace.class);
        Assert.assertEquals(workspace.getName(),workspaceResponse.getName());
    }

    @Test
    public void updateWorkspace() throws JsonProcessingException {
        Workspace workspace = new Workspace();
        List<Object> project_ids=new ArrayList<>();
        project_ids.add(2505070);
        project_ids.add(2505298);
        workspace.setProjectIds(project_ids);
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/my/workspaces/{workspace_id}")
                .withPathParams("workspace_id", "876149")
                .withBody(new ObjectMapper().writeValueAsString(workspace)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_OK);
    }

    @Test
    public void deleteProject() throws JsonProcessingException {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/my/workspaces/{workspace_id}")
                .withPathParams("workspace_id", "876146").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void deleteProjectWithWrongID() throws JsonProcessingException {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/my/workspaces/{workspace_id}")
                .withPathParams("workspace_id", "876146123").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @BeforeMethod(onlyForGroups = "create")
    public void createdWorkspaceBefore() throws JsonProcessingException {
        Workspace workspaceTemp = new Workspace();
        workspaceTemp.setName("Workspace20");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/my/workspaces")
                .withBody(new ObjectMapper().writeValueAsString(workspaceTemp)).build();
        workspaceEndToEnd = ApiManager.executeWithBody(apiRequest).getBody(Workspace.class);
    }

    @Test(groups = {"create","delete"})
    public void createProjectEndToEnd() throws JsonProcessingException {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/my/workspaces/{workspace_id}")
                .withPathParams("workspace_id",String.valueOf( workspaceEndToEnd.getId())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Workspace workspace = apiResponse.getBody(Workspace.class);
        Assert.assertEquals(workspace.getName(),workspaceEndToEnd.getName());
    }

    @AfterMethod(onlyForGroups = "delete")
    public void deleteCreatedProjectConfig() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/my/workspaces/{workspace_id}")
                .withPathParams("workspace_id", String.valueOf( workspaceEndToEnd.getId())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }
}
