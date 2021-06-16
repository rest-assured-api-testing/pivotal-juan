import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import entities.Project;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProjectTest {
    @Test
    public void getAllProjects(){
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setToken("fae1f820733e34ef6385785b843dd339");
        apiRequest.setBaseUri("https://www.pivotaltracker.com/services/v5");
        apiRequest.setEndPoint("/projects");
        apiRequest.setMethod(ApiMethod.GET);

        ApiResponse apiResponse = new ApiResponse(ApiManager.execute(apiRequest));
        Assert.assertEquals(apiResponse.getStatusCode(),200);
    }

    @Test
    public void getProject(){
        ApiRequest apiRequest = new ApiRequest();
//        apiRequest.setToken("fae1f820733e34ef6385785b843dd339");
        apiRequest.addHeader("X-TrackerToken","fae1f820733e34ef6385785b843dd339");
        apiRequest.setBaseUri("https://www.pivotaltracker.com/services/v5");
        apiRequest.setEndPoint("/projects/{projectId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId","2504486");

        ApiResponse apiResponse = new ApiResponse(ApiManager.execute(apiRequest));
        Project project = apiResponse.getBody(Project.class);
        System.out.println(project.getName());
        Assert.assertEquals(apiResponse.getStatusCode(),200);
        Assert.assertEquals(project.getName(),"project1");
        apiResponse.validateSchema("schemas/Project.json");
    }
}
