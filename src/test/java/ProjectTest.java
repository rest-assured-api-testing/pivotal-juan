import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ProjectTest {

    ApiRequest apiRequest = new ApiRequest();
    ApiRequestBuilder apiRequestBuilder = new ApiRequestBuilder();
    Project projectEndToEnd = new Project();

    @BeforeTest
    public void setupConfiguration(){
         apiRequest = apiRequestBuilder
                .withBaseUri("https://www.pivotaltracker.com/services/v5")
                .withHeaders("X-TrackerToken", "fae1f820733e34ef6385785b843dd339")
                .withToken("fae1f820733e34ef6385785b843dd339")
                .build();
    }

    @Test
    public void getAllProjects() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_OK);
    }

    @Test
    public void getProjectWithStatusCode200(){
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId", "2504486").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_OK);
    }

    @Test
    public void getProjectWithName(){
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId", "2504486").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Project project = apiResponse.getBody(Project.class);
        Assert.assertEquals(project.getName(),"project1");
    }

    @Test
    public void addProject() throws JsonProcessingException {
        Project project = new Project();
        project.setName("Project10");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/projects")
                .withBody(new ObjectMapper().writeValueAsString(project)).build();
        Project projectResponse = ApiManager.executeWithBody(apiRequest).getBody(Project.class);
        Assert.assertEquals(project.getName(),projectResponse.getName());
    }

    @Test
    public void updateProject() throws JsonProcessingException {
        Project project = new Project();
        project.setName("project updated");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId", "2504997")
                .withBody(new ObjectMapper().writeValueAsString(project)).build();
        Project projectResponse = ApiManager.executeWithBody(apiRequest).getBody(Project.class);
        Assert.assertEquals(project.getName(),projectResponse.getName());
    }

    @Test
    public void updateProjectWithWrongId() throws JsonProcessingException {
        Project project = new Project();
        project.setName("project updated");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId", "250499712")
                .withBody(new ObjectMapper().writeValueAsString(project)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void deleteProject() throws JsonProcessingException {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId", "2505073").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void deleteProjectWithWrongID() throws JsonProcessingException {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId", "250507312").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @BeforeMethod(onlyForGroups = "create")
    public void createdProjectBefore() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Project20");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/projects")
                .withBody(new ObjectMapper().writeValueAsString(projectTemp)).build();
        projectEndToEnd = ApiManager.executeWithBody(apiRequest).getBody(Project.class);
    }

    @Test(groups = {"create","delete"})
    public void createProjectEndToEnd() throws JsonProcessingException {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Project project = apiResponse.getBody(Project.class);
        Assert.assertEquals(project.getName(),projectEndToEnd.getName());
    }

    @AfterMethod(onlyForGroups = "delete")
    public void deleteCreatedProjectConfig() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }
}
