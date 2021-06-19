import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.AccountMembership;
import entities.Person;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountMembershipTest extends BaseTest {

    @Test(groups = {"findAccount"})
    public void getAccountMembership() {
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.GET).withEndpoint("/accounts/{account_id}/memberships")
                .withPathParams("account_id", accountEndToEnd.getId().toString()).build();
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = {"findAccount","deleteMembership"})
    public void createAccountMembership() throws JsonProcessingException {
        Person person = new Person();
        person.setEmail("example@email.com");
        person.setInitials("MM");
        person.setName("Michael");
        apiRequest = apiRequestBuilder.withMethod(ApiMethod.POST).withEndpoint("/accounts/{account_id}/memberships")
                .withPathParams("account_id",accountEndToEnd.getId().toString())
                .withBody(new ObjectMapper().writeValueAsString(person)).build();
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        accountMembershipEndToEnd = apiResponse.getBody(AccountMembership.class);
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
    }
}
