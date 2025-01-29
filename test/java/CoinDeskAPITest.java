import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CoinDeskAPITest {

    @Test
    public void testBitcoinPriceAPI() {
        // Set base URI
        RestAssured.baseURI = "https://api.coindesk.com/v1/bpi/currentprice.json";

        // Send GET request
        Response response =
                given()
                        .when()
                        .get()
                        .then()
                        .statusCode(200) // Validate response code
                        .body("bpi.size()", equalTo(3)) // Validate 3 currencies are present
                        .body("bpi.USD.code", equalTo("USD")) // Validate USD exists
                        .body("bpi.GBP.code", equalTo("GBP")) // Validate GBP exists
                        .body("bpi.EUR.code", equalTo("EUR")) // Validate EUR exists
                        .body("bpi.GBP.description", equalTo("British Pound Sterling")) // Validate GBP description
                        .extract().response();

        // Print response for debugging
        System.out.println(response.asPrettyString());
    }
}
