import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.*;
import io.cucumber.java.bs.A;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.util.List;


public class BaseTest {

    protected ApiRequest apiRequest = new ApiRequest();
    protected ApiRequestBuilder apiRequestBuilder = new ApiRequestBuilder();
    protected Project projectEndToEnd = new Project();
    protected Workspace workspaceEndToEnd = new Workspace();
    protected Story storyEndToEnd = new Story();
    protected Epic epicEndToEnd = new Epic();
    protected Label labelEndToEnd = new Label();
    protected Account accountEndToEnd = new Account();
    protected ProjectWebHooks webHooksEndToEnd = new ProjectWebHooks();
    protected AccountMembership accountMembershipEndToEnd = new AccountMembership();
    protected Person personEndToEnd = new Person();

    @BeforeClass
    public void setup(){
        apiRequest = apiRequestBuilder
                .withBaseUri("https://www.pivotaltracker.com/services/v5")
                .withHeaders("X-TrackerToken", "fae1f820733e34ef6385785b843dd339")
                .withToken("fae1f820733e34ef6385785b843dd339")
                .build();
    }

    @BeforeMethod(onlyForGroups = "createProject")
    public void createdProjectBefore() throws JsonProcessingException {
        apiRequest.clearPathParams();
        apiRequest.clearQueryParams();
        Project projectTemp = new Project();
        projectTemp.setName("Project30");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/projects")
                .withBody(new ObjectMapper().writeValueAsString(projectTemp)).build();
        projectEndToEnd = ApiManager.executeWithBody(apiRequest).getBody(Project.class);
    }

    @BeforeMethod(onlyForGroups = "createWorkspace")
    public void createdWorkspaceBefore() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Workspace workspaceTemp = new Workspace();
        workspaceTemp.setName("Workspace20");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/my/workspaces")
                .withBody(new ObjectMapper().writeValueAsString(workspaceTemp)).build();
        workspaceEndToEnd = ApiManager.executeWithBody(apiRequest).getBody(Workspace.class);
    }

    @BeforeMethod(dependsOnMethods={"createdProjectBefore"},onlyForGroups = "createStory")
    public void createdStoryBefore() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Story storyTemp = new Story();
        storyTemp.setName("Story temp 1");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/projects/{projectId}/stories")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(storyTemp)).build();
        storyEndToEnd = ApiManager.executeWithBody(apiRequest).getBody(Story.class);
    }

    @BeforeMethod(dependsOnMethods={"createdProjectBefore"},onlyForGroups = "createEpic")
    public void createEpicBefore() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Epic epic = new Epic();
        epic.setName("New Epic temp");
        System.out.println("EPIC "+epic.getName());
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{project_id}/epics")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(epic)).build();
        epicEndToEnd = ApiManager.executeWithBody(apiRequest).getBody(Epic.class);
    }

    @BeforeMethod(dependsOnMethods={"createdProjectBefore"}, onlyForGroups = "createLabel")
    public void createLabelBefore() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Label label = new Label();
        label.setName("label1");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{project_id}/labels")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(label)).build();
        labelEndToEnd = ApiManager.executeWithBody(apiRequest).getBody(Label.class);
    }

    @BeforeMethod(dependsOnMethods={"createdProjectBefore"}, onlyForGroups = "createWebHook")
    public void createProjectWebHookBefore() throws JsonProcessingException {
        apiRequest.clearPathParams();
        ProjectWebHooks projectWebHooks = new ProjectWebHooks();
        projectWebHooks.setWebhook_url("https:///story/show/561");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{project_id}/webhooks")
                .withPathParams("project_id", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(projectWebHooks)).build();
        webHooksEndToEnd = ApiManager.executeWithBody(apiRequest).getBody(ProjectWebHooks.class);
    }

    @BeforeMethod(onlyForGroups = "findAccount")
    public void findAccountBefore() throws JsonProcessingException {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/accounts").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        List<Account> accounts = apiResponse.getBodyList(Account.class);
        accountEndToEnd = accounts.get(accounts.size()-1);
    }

    @AfterMethod(onlyForGroups = "deleteStory")
    public void deleteStoryAfter() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.getResponse().then().log().all();
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    @AfterMethod(onlyForGroups = "deleteProject")
    public void deleteCreatedProjectConfig() {
        apiRequest.clearPathParams();
        apiRequest.clearQueryParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    @AfterMethod(dependsOnMethods={"deleteStoryAfter"},onlyForGroups = "deleteProjectStory")
    public void deleteCreatedProjectStory() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    @AfterMethod(onlyForGroups = "deleteWorkspace")
    public void deleteWorkspaceAfter() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/my/workspaces/{workspace_id}")
                .withPathParams("workspace_id", String.valueOf( workspaceEndToEnd.getId())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    @AfterMethod(onlyForGroups = "deleteMembership")
    public void deleteMembership() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/accounts/{account_id}/memberships/{id}")
                .withPathParams("account_id",accountEndToEnd.getId().toString())
                .withPathParams("id", accountMembershipEndToEnd.getPerson().getId().toString())
                .build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

}
