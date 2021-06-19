import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Story;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StoryTest extends BaseTest {

    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void createStoryEndToEnd() throws JsonProcessingException {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", storyEndToEnd.getProject_id().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Story project = apiResponse.getBody(Story.class);
        Assert.assertEquals(project.getName(),storyEndToEnd.getName());
    }

    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void getAllStories() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories")
                .withPathParams("projectId", storyEndToEnd.getProject_id().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void getAllStoriesWithWrongID() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories")
                .withPathParams("projectId", String.valueOf(storyEndToEnd.getId())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void getProjectStory() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString() )
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void getProjectStoryWithWrongProjectID() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", String.valueOf(projectEndToEnd.getAccount_id()))
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void getProjectStoryWitWrongStoryId() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString() )
                .withPathParams("storyId", storyEndToEnd.getProject_id().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void getProjectStoryWithKind() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Story story = apiResponse.getBody(Story.class);
        Assert.assertEquals(story.getKind(), storyEndToEnd.getKind());
    }

    @Test(groups = {"createProject","deleteStory","deleteProjectStory"})
    public void createStory() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Story story = new Story();
        story.setName("new story");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/projects/{projectId}/stories")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(story)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        storyEndToEnd = apiResponse.getBody(Story.class);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","deleteProject"})
    public void createStoryWithEmptyBody() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Story story = new Story();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/projects/{projectId}/stories")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(story)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_BAD_REQUEST);
    }

    @Test(groups = {"createProject","deleteProject"})
    public void createStoryWithWithWrongId() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Story story = new Story();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/projects/{projectId}/stories")
                .withPathParams("projectId",String.valueOf( projectEndToEnd.getAccount_id()))
                .withBody(new ObjectMapper().writeValueAsString(story)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_NOT_FOUND);
    }


    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void updateStory() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Story story = new Story();
        story.setName("Story update");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(story)).build();
        Story storyResponse = ApiManager.executeWithBody(apiRequest).getBody(Story.class);
        Assert.assertEquals(story.getName(),storyResponse.getName());
    }

    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void updateStoryWithWrongProjectId() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Story story = new Story();
        story.setName("Story update");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", String.valueOf(projectEndToEnd.getAccount_id()))
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(story)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void updateStoryWithWrongStoryId() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Story story = new Story();
        story.setName("Story update");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getProject_id().toString())
                .withBody(new ObjectMapper().writeValueAsString(story)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_BAD_REQUEST);
    }

    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void updateStoryWithEmptyBody() throws JsonProcessingException {
        apiRequest.clearPathParams();
        Story story = new Story();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(story)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createStory","deleteProject"})
    public void deleteStory() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    @Test(groups = {"createProject","createStory","deleteProject"})
    public void deleteStoryWithWrongProjectID() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", String.valueOf(projectEndToEnd.getAccount_id()))
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }
    @Test(groups = {"createProject","createStory","deleteProject"})
    public void  deleteStoryWithWrongStoryID() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getProject_id().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
    }
}
