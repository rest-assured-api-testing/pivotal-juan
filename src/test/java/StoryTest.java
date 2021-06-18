import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import entities.Story;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class StoryTest {

    ApiRequest apiRequest = new ApiRequest();
    ApiRequestBuilder apiRequestBuilder = new ApiRequestBuilder();
    Story storyEndToEnd = new Story();

    @BeforeTest
    public void setupConfiguration(){
        apiRequest = apiRequestBuilder
                .withBaseUri("https://www.pivotaltracker.com/services/v5")
                .withHeaders("X-TrackerToken", "fae1f820733e34ef6385785b843dd339")
                .withToken("fae1f820733e34ef6385785b843dd339")
                .build();
    }

    @Test
    public void getAllStories() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories")
                .withPathParams("projectId", "2504486").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void createStory() throws JsonProcessingException {
        Story story = new Story();
        story.setName("Story20");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/projects/{projectId}/stories")
                .withPathParams("projectId", "2504486")
                .withBody(new ObjectMapper().writeValueAsString(story)).build();
        Story storyResponse = ApiManager.executeWithBody(apiRequest).getBody(Story.class);
        Assert.assertEquals(story.getName(),storyResponse.getName());
    }

    @Test
    public void getProjectStory() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", "2504486")
                .withPathParams("storyId","178579711").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void getProjectStoryWithKind() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", "2504486")
                .withPathParams("storyId","178579711").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Story story = apiResponse.getBody(Story.class);
        Assert.assertEquals(story.getKind(), "story");
    }

    @Test
    public void updateStory() throws JsonProcessingException {
        Story story = new Story();
        story.setName("Story update");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.PUT).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", "2504486")
                .withPathParams("storyId","178549153")
                .withBody(new ObjectMapper().writeValueAsString(story)).build();
        Story storyResponse = ApiManager.executeWithBody(apiRequest).getBody(Story.class);
        Assert.assertEquals(story.getName(),storyResponse.getName());
    }

    @Test
    public void deleteStory() throws JsonProcessingException {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", "2504486")
                .withPathParams("storyId","178579871").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    @BeforeMethod(onlyForGroups = "create")
    public void createdStoryBefore() throws JsonProcessingException {
        Story storyTemp = new Story();
        storyTemp.setName("Story temp");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/projects/{projectId}/stories")
                .withPathParams("projectId", "2504486")
                .withBody(new ObjectMapper().writeValueAsString(storyTemp)).build();
        storyEndToEnd = ApiManager.executeWithBody(apiRequest).getBody(Story.class);
    }

    @Test(groups = {"create","delete"})
    public void createStoryEndToEnd() throws JsonProcessingException {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", storyEndToEnd.getProject_id().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Story project = apiResponse.getBody(Story.class);
        Assert.assertEquals(project.getName(),storyEndToEnd.getName());
    }

    @AfterMethod(onlyForGroups = "delete")
    public void deleteCreatedProjectConfig() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.DELETE).withEndpoint("/projects/{projectId}/stories/{storyId}")
                .withPathParams("projectId", storyEndToEnd.getProject_id().toString())
                .withPathParams("storyId", storyEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

}
