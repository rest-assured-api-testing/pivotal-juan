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

    @Test(groups = {"createProject","createWorkspace","deleteProject","deleteWorkspace"})
    public void updateWorkspace() throws JsonProcessingException {
        Workspace workspace = new Workspace();
        List<Object> project_ids=new ArrayList<>();
        project_ids.add(projectEndToEnd.getId());
        workspace.setProjectIds(project_ids);
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/my/workspaces/{workspace_id}")
                .withPathParams("workspace_id", String.valueOf( workspaceEndToEnd.getId()))
                .withBody(new ObjectMapper().writeValueAsString(workspace)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_OK);
    }

    @Test(groups = {"createWorkspace","deleteWorkspace"})
    public void deleteProjectWithWrongID() throws JsonProcessingException {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/my/workspaces/{workspace_id}")
                .withPathParams("workspace_id", String.valueOf( workspaceEndToEnd.getPersonId())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }
}
