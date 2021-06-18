import api.*;
import entities.Project;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MeTest {
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
    public void getMe() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/me").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getMeWithName() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/me").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        String name = apiResponse.getResponse().then().extract().body().jsonPath().getString("name");
        Assert.assertEquals(name,"juampi7237");

    }

    @Test
    public void getMeWithWrongUserName() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/me").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        String username = apiResponse.getResponse().then().extract().body().jsonPath().getString("username");
        Assert.assertNotEquals(username,"pablo");
    }

    @Test
    public void getMeWithCopy() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/my/people")
                .withQueryParams("project_id","2504486").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void getMeWithCopyWrongId() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/my/people")
                .withQueryParams("project_id","250448612").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_FORBIDDEN);
    }
}
