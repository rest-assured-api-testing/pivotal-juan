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
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", storyEndToEnd.getProject_id().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Story project = apiResponse.getBody(Story.class);
        Assert.assertEquals(project.getName(),storyEndToEnd.getName());
    }

    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void getAllStories() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories")
                .withPathParams("projectId", storyEndToEnd.getProject_id().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void getProjectStory() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString() )
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void getProjectStoryWithKind() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Story story = apiResponse.getBody(Story.class);
        Assert.assertEquals(story.getKind(), storyEndToEnd.getKind());
    }

    @Test(groups = {"createProject","createStory","deleteStory","deleteProjectStory"})
    public void updateStory() throws JsonProcessingException {
        Story story = new Story();
        story.setName("Story update");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", projectEndToEnd.getId().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(story)).build();
        Story storyResponse = ApiManager.executeWithBody(apiRequest).getBody(Story.class);
        Assert.assertEquals(story.getName(),storyResponse.getName());
    }
}
