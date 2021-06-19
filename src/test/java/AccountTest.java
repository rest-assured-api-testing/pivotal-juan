import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountTest extends BaseTest {


    @Test
    public void getAccounts() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/accounts").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void getAccountSummaries() {
        apiRequest.clearPathParams();
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/account_summaries").build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"findAccount"})
    public void getAccount() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/accounts/{id}")
                .withPathParams("id", accountEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }
}
