import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestLoginUser {
    TestPOMLoginUser obj;
    TestPOMLoginUser objWrongField;
    String accessToken;

    @Before
    public void init() {
        SetUp.setUp();
    }

    @Test
    public void testLoginExistingUser(){
        obj = new TestPOMLoginUser("test_testLoginExistingUser11111@mail.ru", "12345", "Vladimir");
        Response response = obj.createUser();
        obj.loginUser()
                .then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
        ;
        accessToken = response.path("accessToken");
        if(accessToken != null){
            TestPOMLoginUser.deleteUser(accessToken);
        }

    }

    @Test
    public void testLoginUserEmptyField(){
        obj = new TestPOMLoginUser("test_testLoginUserEmptyField1@mail.ru", "12345", "Vladimir");
        objWrongField = new TestPOMLoginUser("test_testLoginUserEmptyField1@mail.ru", "1", "Vladimir");
        Response response = obj.createUser();
        objWrongField.loginUser()
                .then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
        ;

        accessToken = response.path("accessToken");
        if(accessToken != null){
            TestPOMLoginUser.deleteUser(accessToken);
        }

    }


}
