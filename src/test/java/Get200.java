import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static entities.BaseEndpoint.rateLimitEndpoint;
import static org.testng.Assert.assertEquals;

public class Get200 extends BaseClass {

    CloseableHttpClient client;
    CloseableHttpResponse response;
    int expectedHttpCode = 200;

    @BeforeMethod
    public void setup() {
        client = HttpClientBuilder.create().build();
    }

    @AfterMethod
    public void closeResponse() throws IOException {
        client.close();
        response.close();
    }

    @DataProvider
    private Object[][] endpoints() {
        return new Object[][] {
                {""},
                {rateLimitEndpoint},
                {"/search/repositories?q=java"}
        };
    }

    @Test(dataProvider = "endpoints")
    public void urlReturns200(String endpoint) throws IOException {

        HttpGet get = new HttpGet(BASE_ENDPOINT + endpoint);
        response = client.execute(get);
        int actualStatus = response.getStatusLine().getStatusCode();

        assertEquals(actualStatus, expectedHttpCode);
    }

}
