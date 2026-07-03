package tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.TestPOMCreateUser;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestCreateUser {
    TestPOMCreateUser obj;
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
        obj = new TestPOMCreateUser(email, password, name);
    }

    @Test
    @DisplayName("создать уникального пользователя;")
    public void testCreateUser(){
        Response response = obj.createUser();

        response.then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
        ;
        accessToken = response.path("accessToken");
    }

    @Test
    @DisplayName("создать пользователя, который уже зарегистрирован;")
    public void testReCreateUser(){
        Response response = obj.createUser();

        obj.createUser()
                .then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"))
        ;
        accessToken = response.path("accessToken");
    }

    @Test
    @DisplayName("создать пользователя и не заполнить поле Имя.")
    public void testCreateUserWithoutFieldName(){
        obj = new TestPOMCreateUser(email, password, null);
        obj.createUser()
                .then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
        ;
    }

    @Test
    @DisplayName("создать пользователя и не заполнить поле Почта.")
    public void testCreateUserWithoutFieldEmail(){
        obj = new TestPOMCreateUser(null, password, name);
        obj.createUser()
                .then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
        ;
    }

    @Test
    @DisplayName("создать пользователя и не заполнить поле Пароль.")
    public void testCreateUserWithoutFieldPassword(){
        obj = new TestPOMCreateUser(email, null, name);
        obj.createUser()
                .then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
        ;
    }

    @After
    public void endTest(){
        if(accessToken != null){
            TestPOMCreateUser.deleteUser(accessToken);
        }
    }
}
