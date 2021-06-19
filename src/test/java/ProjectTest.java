import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProjectTest extends BaseTest {

    @Test(groups = {"createProject","deleteProject"})
    public void getAllProjects() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","deleteProject"})
    public void getProjectWithStatusCode200(){
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","deleteProject"})
    public void getProjectWithWrongID(){
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId", String.valueOf(projectEndToEnd.getAccount_id())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","deleteProject"})
    public void getProjectWithName(){
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Project project = apiResponse.getBody(Project.class);
        Assert.assertEquals(project.getName(),projectEndToEnd.getName());
    }

    @Test(groups = {"deleteProject"})
    public void createProject() throws JsonProcessingException{
        Project project = new Project();
        project.setName("Project20");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/projects")
                .withBody(new ObjectMapper().writeValueAsString(project)).build();
        projectEndToEnd = ApiManager.executeWithBody(apiRequest).getBody(Project.class);
        Assert.assertEquals(projectEndToEnd.getName(),project.getName());
    }

    @Test(groups = {"createProject","deleteProject"})
    public void createProjectWithEmptyBody() throws JsonProcessingException{
        Project project = new Project();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/projects")
                .withBody(new ObjectMapper().writeValueAsString(project)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
    }

    @Test(groups = {"createProject","deleteProject"})
    public void updateProject() throws JsonProcessingException {
        Project project = new Project();
        project.setName("project updated");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId",  projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(project)).build();
        Project projectResponse = ApiManager.executeWithBody(apiRequest).getBody(Project.class);
        Assert.assertEquals(project.getName(),projectResponse.getName());
    }

    @Test(groups = {"createProject","deleteProject"})
    public void updateProjectWithWrongId() throws JsonProcessingException {
        Project project = new Project();
        project.setName("project updated");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId",String.valueOf(projectEndToEnd.getAccount_id()))
                .withBody(new ObjectMapper().writeValueAsString(project)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject"})
    public void deleteProject() throws JsonProcessingException {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId",  projectEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    @Test(groups = {"createProject","deleteProject"})
    public void deleteProjectWithWrongID() throws JsonProcessingException {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId",  String.valueOf(projectEndToEnd.getAccount_id())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","deleteProject"})
    public void createProjectEndToEnd() throws JsonProcessingException {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId",projectEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Project project = apiResponse.getBody(Project.class);
        Assert.assertEquals(project.getName(),projectEndToEnd.getName());
    }


}
