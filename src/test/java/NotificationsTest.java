import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NotificationsTest extends BaseTest{

    @Test
    public void getNotificationsTest() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/my/notifications")
                .withQueryParams("envelope", "true").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }
}
