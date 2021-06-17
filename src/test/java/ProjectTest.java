import api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProjectTest {

    ApiRequest apiRequest = new ApiRequest();

    @Test
    public void getAllProjects(){
        apiRequest.setToken("fae1f820733e34ef6385785b843dd339");
        apiRequest.setBaseUri("https://www.pivotaltracker.com/services/v5");
        apiRequest.setEndPoint("/projects");
        apiRequest.setMethod(ApiMethod.GET);

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),200);
    }

    @Test
    public void getAllProjects2() {
        ApiRequest apiRequest = new ApiRequestBuilder()
                .withBaseUri("https://www.pivotaltracker.com/services/v5")
                .withToken("fae1f820733e34ef6385785b843dd339")
                .withEndpoint("/projects")
                .withMethod(ApiMethod.GET)
                .build();

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(),200);
    }

    @Test
    public void getProject(){
        apiRequest.addHeader("X-TrackerToken","fae1f820733e34ef6385785b843dd339");
        apiRequest.setToken("fae1f820733e34ef6385785b843dd339");
        apiRequest.setBaseUri("https://www.pivotaltracker.com/services/v5");
        apiRequest.setEndPoint("/projects/{projectId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId","2504486");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Project project = apiResponse.getBody(Project.class);
        System.out.println(project.getName());
        Assert.assertEquals(apiResponse.getStatusCode(),200);
        Assert.assertEquals(project.getName(),"project1");
        apiResponse.validateSchema("schemas/Project.json");
    }

    @Test
    public void getProject2() {
        ApiRequest apiRequest = new ApiRequestBuilder()
                .withBaseUri("https://www.pivotaltracker.com/services/v5")
                .withToken("fae1f820733e34ef6385785b843dd339")
                .withHeaders("X-TrackerToken", "fae1f820733e34ef6385785b843dd339")
                .withEndpoint("/projects/{projectId}")
                .withPathParams("projectId", "2504486")
                .withMethod(ApiMethod.GET)
                .build();

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Project project = apiResponse.getBody(Project.class);
        System.out.println(project.getName());
        Assert.assertEquals(apiResponse.getStatusCode(),200);
        Assert.assertEquals(project.getName(),"project1");
        apiResponse.validateSchema("schemas/Project.json");
    }

    @Test
    public void addProject() throws JsonProcessingException {
        Project project = new Project();
        project.setName("PROYECTITO");
        ApiRequest apiRequest = new ApiRequestBuilder()
                .withBaseUri("https://www.pivotaltracker.com/services/v5")
//                .withToken("fae1f820733e34ef6385785b843dd339")
                .withHeaders("X-TrackerToken", "fae1f820733e34ef6385785b843dd339")
                .withEndpoint("/projects")
                .withMethod(ApiMethod.POST)
                .withBody(new ObjectMapper().writeValueAsString(project))
                .build();
        System.out.println("API REQUEST "+apiRequest);
        System.out.println(new ObjectMapper().writeValueAsString(project));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        System.out.println(apiResponse);
        apiResponse.getResponse().then().log().body();
//        project = apiResponse.getBody(Project.class);
//        System.out.println(project.getName());
//        Assert.assertEquals(apiResponse.getStatusCode(),200);
//        Assert.assertEquals(project.getName(),"PROYECTITO");
//        apiResponse.validateSchema("schemas/Project.json");
    }

    @Test
    public void createProject2() throws JsonProcessingException {
        Project project = new Project();
        project.setName("PROYECTITO2");
        apiRequest.addHeader("X-TrackerToken","fae1f820733e34ef6385785b843dd339");
        apiRequest.setToken("fae1f820733e34ef6385785b843dd339");
        apiRequest.setBaseUri("https://www.pivotaltracker.com/services/v5");
        apiRequest.setEndPoint("/projects");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(project));


        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.getResponse().then().log().body();
//        project = apiResponse.getBody(Project.class);
//        System.out.println(project.getName());
        Assert.assertEquals(apiResponse.getStatusCode(),200);
//        Assert.assertEquals(project.getName(),"PROYECTITO2");
//        apiResponse.validateSchema("schemas/Project.json");
    }
}
