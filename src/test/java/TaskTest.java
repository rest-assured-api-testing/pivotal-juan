import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Task;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TaskTest extends BaseTest{

    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void getTask() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withPathParams("taskId", taskEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void getTaskWithWrongProjectId() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", String.valueOf(projectEndToEnd.getAccount_id()))
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withPathParams("taskId", taskEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void getTaskWithWrongStoryId() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getProject_id().toString())
                .withPathParams("taskId", taskEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void getTaskWithWrongTaskId() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withPathParams("taskId", String.valueOf(taskEndToEnd.getStory_id())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void getAllTaskStory() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void getAllTaskStoryWithWrongId() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/")
                .withPathParams("projectId", String.valueOf(projectEndToEnd.getAccount_id()))
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void getAllTaskStoryWithWrongStoryId() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getProject_id().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createStory","createTask","deleteStory","deleteProjectStory"})
    public void deleteTask() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withPathParams("taskId", taskEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    @Test(groups = {"createProject","createStory","createTask","deleteStory","deleteProjectStory"})
    public void deleteTaskWithWrongProjectId() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", String.valueOf(projectEndToEnd.getAccount_id()))
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withPathParams("taskId", taskEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createStory","createTask","deleteStory","deleteProjectStory"})
    public void deleteTaskWithWrongStoryId() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getProject_id().toString())
                .withPathParams("taskId", taskEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
    }

    @Test(groups = {"createProject","createStory","createTask","deleteStory","deleteProjectStory"})
    public void deleteTaskWithWrongTaskId() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withPathParams("taskId", String.valueOf(taskEndToEnd.getStory_id())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
    }

    @Test(groups = {"createProject","createStory","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void createTask() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Task task = new Task();
        task.setDescription("new Task");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{projectId}/stories/{storyId}/tasks")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(task)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        taskEndToEnd = apiResponse.getBody(Task.class);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createStory","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void createTaskWithDescription() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Task task = new Task();
        task.setDescription("new Task");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{projectId}/stories/{storyId}/tasks")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(task)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        taskEndToEnd = apiResponse.getBody(Task.class);
        Assert.assertEquals(taskEndToEnd.getDescription(), task.getDescription());
    }

    @Test(groups = {"createProject","createStory","deleteTaskStory","deleteTaskStoryProject"})
    public void createTaskWithWrongProjectId() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Task task = new Task();
        task.setDescription("new Task");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{projectId}/stories/{storyId}/tasks")
                .withPathParams("projectId",String.valueOf( projectEndToEnd.getAccount_id()))
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(task)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createStory","deleteTaskStory","deleteTaskStoryProject"})
    public void createTaskWithWrongStoryId() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Task task = new Task();
        task.setDescription("new Task");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{projectId}/stories/{storyId}/tasks")
                .withPathParams("projectId",String.valueOf( projectEndToEnd.getAccount_id()))
                .withPathParams("storyId", storyEndToEnd.getProject_id().toString())
                .withBody(new ObjectMapper().writeValueAsString(task)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createStory","deleteTaskStory","deleteTaskStoryProject"})
    public void createTaskWithEmptyBody() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Task task = new Task();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{projectId}/stories/{storyId}/tasks")
                .withPathParams("projectId",String.valueOf( projectEndToEnd.getAccount_id()))
                .withPathParams("storyId", storyEndToEnd.getProject_id().toString())
                .withBody(new ObjectMapper().writeValueAsString(task)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }


    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void updateTask() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Task task = new Task();
        task.setDescription("new description");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withPathParams("taskId", taskEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(task)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        taskEndToEnd = apiResponse.getBody(Task.class);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }


    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void updateTaskWithDescription() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Task task = new Task();
        task.setDescription("new description");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withPathParams("taskId", taskEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(task)).build();
        taskEndToEnd  = ApiManager.executeWithBody(apiRequest).getBody(Task.class);
        Assert.assertEquals(taskEndToEnd.getDescription(), task.getDescription());
    }
    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void updateTaskWithWrongProjectId() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Task task = new Task();
        task.setDescription("new description");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", String.valueOf(projectEndToEnd.getAccount_id()))
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withPathParams("taskId", taskEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(task)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }
    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void updateTaskWithWrongStoryId() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Task task = new Task();
        task.setDescription("new description");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getProject_id().toString())
                .withPathParams("taskId", taskEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(task)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
    }
    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void updateTaskWithWrongTaskId() throws JsonProcessingException {
        Task task = new Task();
        task.setDescription("new description");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withPathParams("taskId", String.valueOf(taskEndToEnd.getStory_id()))
                .withBody(new ObjectMapper().writeValueAsString(task)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
    }

    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void updateTaskWithEmptyBody() throws JsonProcessingException {
        Task task = new Task();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withPathParams("taskId", taskEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(task)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }
}
