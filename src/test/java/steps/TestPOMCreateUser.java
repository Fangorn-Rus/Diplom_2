package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.CreateUserDTO;
import tests.Endpoints;
import tests.SetUp;

import static io.restassured.RestAssured.given;

public class TestPOMCreateUser {
    private final String email;
    private final String password;
    private final String name;
    CreateUserDTO obj;

    public TestPOMCreateUser(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step
    public static void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .spec(SetUp.requestSpec)
                .when()
                .delete(Endpoints.DELETE_USER)
                ;
    }
    @Step
    public Response createUser() {
         obj = new CreateUserDTO(email, password, name);

        return given()
                .header("Content-type", "application/json")
                .spec(SetUp.requestSpec)
                .body(obj)
                .when()
                .post(Endpoints.CREATE_USER);
    }
}
