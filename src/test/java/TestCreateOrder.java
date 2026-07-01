import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestCreateOrder {
    CreateAndLoginUser createAndLoginUser;
    TestPOMCreateOrder obj;
    String accessToken;

    @Before
    public void init() {
        SetUp.setUp();
    }

    @Test
    public void testCreateOrderWithAuthAndIngredients(){
        createAndLoginUser = new TestPOMLoginUser("test_testCreateOrderWithAuth@mail.ru", "12345", "Vladimir");
        Response response = createAndLoginUser.createUser();
        createAndLoginUser.loginUser();

        obj = new TestPOMCreateOrder(new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa70"}, createAndLoginUser);
        obj.createOrder()
                .then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));

        accessToken = response.path("accessToken");
        if(accessToken != null){
            TestPOMLoginUser.deleteUser(accessToken);
        }

    }

    @Test
    public void testCreateOrderWithoutAuthAndIngredients(){
        createAndLoginUser = new TestPOMLoginUser("test_testCreateOrderWithoutAuthAndIngredients@mail.ru", "12345", "Vladimir");
        Response response = createAndLoginUser.createUser();
        createAndLoginUser.loginUser();

        obj = new TestPOMCreateOrder(new String[]{}, createAndLoginUser);
        obj.createOrder()
                .then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("success", equalTo(false));

        accessToken = response.path("accessToken");
        if(accessToken != null){
            TestPOMLoginUser.deleteUser(accessToken);
        }

    }

    @Test
    public void testCreateOrderWithAuthAndWrongIngredients(){
        String testEmail = "test_wrong_ingredients_" + System.currentTimeMillis() + "@mail.ru";
        createAndLoginUser = new TestPOMLoginUser(testEmail, "12345", "Vladimir");
        createAndLoginUser.createUser();
        createAndLoginUser.loginUser();

        obj = new TestPOMCreateOrder(new String[]{"61coc5a71d1f82001bdaaa74"}, createAndLoginUser);
        obj.createOrder()
                .then().assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR)
                .and()
                ;

    }

    @Test
    public void testCreateOrderWithoutAuth(){
        obj = new TestPOMCreateOrder(new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa70"}, createAndLoginUser);
        obj.createOrder()
                .then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }


}
