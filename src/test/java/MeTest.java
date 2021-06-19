import api.*;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MeTest extends BaseTest{


    @Test(groups = {"createProject","deleteProject"})
    public void getMe() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/me").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = {"createProject","deleteProject"})
    public void getMeWithName() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/me").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        String name = apiResponse.getResponse().then().extract().body().jsonPath().getString("name");
        Assert.assertEquals(name,"juampi7237");

    }

    @Test(groups = {"createProject","deleteProject"})
    public void getMeWithWrongUserName() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/me").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        String username = apiResponse.getResponse().then().extract().body().jsonPath().getString("username");
        Assert.assertNotEquals(username,"pablo");
    }

    @Test(groups = {"createProject","deleteProject"})
    public void getMeWithCopy() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/my/people")
                .withQueryParams("project_id",projectEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"createProject","deleteProject"})
    public void getMeWithCopyWrongId() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/my/people")
                .withQueryParams("project_id",String.valueOf(projectEndToEnd.getAccount_id())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_FORBIDDEN);
    }

    @Test(groups = {"createProject","deleteProject"})
    public void getMeWithCopyWrongEndPoint() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/my/peopl")
                .withQueryParams("project_id",String.valueOf(projectEndToEnd.getAccount_id())).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }
}
