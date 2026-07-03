package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.LoginUserDTO;
import tests.CreateAndLoginUser;
import tests.Endpoints;
import tests.SetUp;

import static io.restassured.RestAssured.given;

public class TestPOMLoginUser implements CreateAndLoginUser {
    private final String email;
    private final String password;
    private final String userName;
    LoginUserDTO obj;

    public TestPOMLoginUser(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }
    @Step("Удаление пользователя")
    public static void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .spec(SetUp.requestSpec)
                .when()
                .delete(Endpoints.DELETE_USER)
                ;
    }
    @Step("Создание пользователя")
    public Response createUser(){
        obj = new LoginUserDTO(email, password, userName);

        return given()
                .header("Content-type", "application/json")
                .spec(SetUp.requestSpec)
                .body(obj)
                .when()
                .post(Endpoints.CREATE_USER);
    }
    @Step("Вход пользователя")
    public Response loginUser() {
        obj = new LoginUserDTO(email, password, userName);

        return given()
                .header("Content-type", "application/json")
                .spec(SetUp.requestSpec)
                .body(obj)
                .when()
                .post(Endpoints.LOGIN_USER);
    }
}
