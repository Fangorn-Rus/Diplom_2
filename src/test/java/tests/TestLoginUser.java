package tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.TestPOMLoginUser;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestLoginUser {
    TestPOMLoginUser obj;
    TestPOMLoginUser objWrongField;
    String accessToken;
    private String email;
    private String password;
    private String name;

    @Before
    public void init() {
        SetUp.setUp();
        email = "test_" + System.currentTimeMillis() + "@mail.ru";
        password = System.currentTimeMillis() + "pass";
        name = "Vladimir";
    }

    @Test
    @DisplayName("вход под существующим пользователем;")
    public void testLoginExistingUser(){
        obj = new TestPOMLoginUser(email, password, name);
        Response response = obj.createUser();
        accessToken = response.path("accessToken");
        obj.loginUser()
                .then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
        ;
    }

    @Test
    @DisplayName("вход с неверным логином.")
    public void testLoginUserWrongFieldLogin(){
        String correctLogin = "test_" + System.currentTimeMillis() + "@mail.ru";
        String wrongLogin = correctLogin + "1";
        obj = new TestPOMLoginUser(
                correctLogin
                , "12345"
                , "Vladimir"
        );
        objWrongField = new TestPOMLoginUser(wrongLogin, "12345", "Vladimir");
        Response response = obj.createUser();
        objWrongField.loginUser()
                .then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
        ;
        accessToken = response.path("accessToken");
    }

    @Test
    @DisplayName("вход с неверным паролем.")
    public void testLoginUserWrongFieldPassword(){
        String correctPassword = System.currentTimeMillis() + "pass";
        String wrongPassword = correctPassword + "1";
        obj = new TestPOMLoginUser(
                "test_" + System.currentTimeMillis() + "@mail.ru"
                , correctPassword
                , "Vladimir"
        );
        objWrongField = new TestPOMLoginUser("test_testLoginUserEmptyField1@mail.ru", wrongPassword, "Vladimir");
        Response response = obj.createUser();
        objWrongField.loginUser()
                .then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
        ;
        accessToken = response.path("accessToken");
    }

    @After
    public void endTest(){
        if(accessToken != null){
            TestPOMLoginUser.deleteUser(accessToken);
        }
    }


}
