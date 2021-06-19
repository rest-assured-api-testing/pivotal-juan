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
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withPathParams("taskId", taskEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void getAllTaskStory() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createStory","createTask","deleteStory","deleteProjectStory"})
    public void deleteTask() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withPathParams("taskId", taskEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    @Test(groups = {"createProject","createStory","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void createTask() throws JsonProcessingException {
        Task task = new Task();
        task.setDescription("new Task");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("projects/{projectId}/stories/{storyId}/tasks")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(task)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createStory","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void createTaskWithDescription() throws JsonProcessingException {
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


    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void updateTask() throws JsonProcessingException {
        Task task = new Task();
        task.setDescription("new description");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withPathParams("taskId", taskEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(task)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }


    @Test(groups = {"createProject","createStory","createTask","deleteTask","deleteTaskStory","deleteTaskStoryProject"})
    public void updateTaskWithDescription() throws JsonProcessingException {
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
}
