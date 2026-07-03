package tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.TestPOMCreateOrder;
import steps.TestPOMLoginUser;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestCreateOrder {
    CreateAndLoginUser createAndLoginUser;
    TestPOMCreateOrder obj;
    String accessToken;

    @Before
    public void init() {
        SetUp.setUp();
        String email = "test_" + System.currentTimeMillis() + "@mail.ru";
        String password = System.currentTimeMillis() + "pass";
        String name = "Vladimir";

        createAndLoginUser = new TestPOMLoginUser(email, password, name);
        Response response = createAndLoginUser.createUser();
        accessToken = response.path("accessToken");

    }

    @Test
    @DisplayName("Создание заказа с авторизацией; с ингредиентами;")
    public void testCreateOrderWithAuthAndIngredients(){
        createAndLoginUser.loginUser();
        obj = new TestPOMCreateOrder(new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa70"}, createAndLoginUser);
        obj.createOrder()
                .then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации; без ингредиентов;")
    public void testCreateOrderWithoutAuthAndIngredients(){
        obj = new TestPOMCreateOrder(new String[]{}, createAndLoginUser);
        obj.createOrder()
                .then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией; с неверным хешем ингредиентов;")
    public void testCreateOrderWithAuthAndWrongIngredients(){
        createAndLoginUser.loginUser();
        obj = new TestPOMCreateOrder(new String[]{"61coc5a71d1f82001bdaaa74"}, createAndLoginUser);
        obj.createOrder()
                .then().assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR)
                ;
    }

    @Test
    @DisplayName("Создание заказа без авторизации;")
    public void testCreateOrderWithoutAuth(){
        obj = new TestPOMCreateOrder(new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa70"}, createAndLoginUser);
        obj.createOrder()
                .then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @After
    public void endTest(){
        if(accessToken != null){
            TestPOMLoginUser.deleteUser(accessToken);
        }
    }


}
