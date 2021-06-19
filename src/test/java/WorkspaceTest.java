import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Workspace;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceTest extends BaseTest{


    @Test(groups = {"createWorkspace","deleteWorkspace"})
    public void createProjectEndToEnd() throws JsonProcessingException {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/my/workspaces/{workspace_id}")
                .withPathParams("workspace_id",String.valueOf( workspaceEndToEnd.getId())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Workspace workspace = apiResponse.getBody(Workspace.class);
        Assert.assertEquals(workspace.getName(),workspaceEndToEnd.getName());
    }

    @Test(groups = {"createWorkspace","deleteWorkspace"})
    public void getAllWorkspace() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/my/workspaces").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_OK);
    }

    @Test(groups = {"createWorkspace","deleteWorkspace"})
    public void getWorkspaceWithStatusCode200(){
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/my/workspaces/{workspace_id}")
                .withPathParams("workspace_id", String.valueOf( workspaceEndToEnd.getId())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createWorkspace","deleteWorkspace"})
    public void getWorkspaceWithName(){
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/my/workspaces/{workspace_id}")
                .withPathParams("workspace_id", String.valueOf( workspaceEndToEnd.getId())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Workspace workspace = apiResponse.getBody(Workspace.class);
        Assert.assertEquals(workspace.getName(),workspaceEndToEnd.getName());
    }

    @Test(groups = {"deleteWorkspace"})
    public void createdWorkspace() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Workspace workspace = new Workspace();
        workspace.setName("Workspace10");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/my/workspaces")
                .withBody(new ObjectMapper().writeValueAsString(workspace)).build();
        workspaceEndToEnd = ApiManager.executeWithBody(apiRequest).getBody(Workspace.class);
        Assert.assertEquals(workspaceEndToEnd.getName(),workspace.getName());
    }

    @Test(groups = {"deleteWorkspace"})
    public void createdWorkspaceStatusCode200() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Workspace workspace = new Workspace();
        workspace.setName("Workspace10");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/my/workspaces")
                .withBody(new ObjectMapper().writeValueAsString(workspace)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        workspaceEndToEnd = apiResponse.getBody(Workspace.class);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","deleteProject","deleteWorkspace"})
    public void createdWorkspaceWithProject() throws JsonProcessingException {
        Workspace workspace = new Workspace();
        List<Object> project_ids =new ArrayList<>();
        project_ids.add(projectEndToEnd.getId());
        workspace.setName("Workspace20");
        workspace.setProjectIds(project_ids);
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/my/workspaces")
                .withBody(new ObjectMapper().writeValueAsString(workspace)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        workspaceEndToEnd = apiResponse.getBody(Workspace.class);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_OK);
    }

    @Test
    public void createdWorkspaceWithEmptyBody() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Workspace workspace = new Workspace();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/my/workspaces")
                .withBody(new ObjectMapper().writeValueAsString(workspace)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_BAD_REQUEST);
    }

    @Test(groups = {"createWorkspace","deleteWorkspace"})
    public void createdWorkspaceWithFalseProjects() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Workspace workspace = new Workspace();
        List<Object> project_ids=new ArrayList<>();
        project_ids.add("213423556");
        project_ids.add("412415151");
        workspace.setProjectIds(project_ids);
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/my/workspaces")
                .withBody(new ObjectMapper().writeValueAsString(workspace)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_BAD_REQUEST);
    }

    @Test(groups = {"createProject","createWorkspace","deleteProject","deleteWorkspace"})
    public void updateWorkspace() throws JsonProcessingException {
        Workspace workspace = new Workspace();
        List<Object> project_ids=new ArrayList<>();
        project_ids.add(projectEndToEnd.getId());
        workspace.setProjectIds(project_ids);
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/my/workspaces/{workspaceId}")
                .withPathParams("workspaceId", String.valueOf( workspaceEndToEnd.getId()))
                .withBody(new ObjectMapper().writeValueAsString(workspace)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_OK);
    }

    @Test(groups = {"createWorkspace","deleteWorkspace"})
    public void updateWorkspaceWithEmptyBody() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Workspace workspace = new Workspace();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/my/workspaces/{workspaceId}")
                .withPathParams("workspaceId", String.valueOf( workspaceEndToEnd.getId()))
                .withBody(new ObjectMapper().writeValueAsString(workspace)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test(groups = {"createWorkspace"})
    public void deleteWorkspace() throws JsonProcessingException {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/my/workspaces/{workspaceId}")
                .withPathParams("workspaceId", String.valueOf( workspaceEndToEnd.getId())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    @Test(groups = {"createWorkspace","deleteWorkspace"})
    public void deleteWorkspaceWithWrongID() throws JsonProcessingException {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/my/workspaces/{workspaceId}")
                .withPathParams("workspaceId", String.valueOf( workspaceEndToEnd.getPersonId())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }
}
