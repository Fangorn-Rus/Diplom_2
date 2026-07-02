import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

public class SetUp {
    public static RequestSpecification requestSpec;

    @BeforeClass
    public static void setUp() {


        RestAssured.baseURI = "https://stellarburgers.education-services.ru";

        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://stellarburgers.education-services.ru")
                .build();
    }
}
