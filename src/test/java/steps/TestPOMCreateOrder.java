package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.CreateOrderDTO;
import tests.CreateAndLoginUser;
import tests.Endpoints;
import tests.SetUp;

import static io.restassured.RestAssured.given;

public class TestPOMCreateOrder implements CreateAndLoginUser {
    private final String[] ingredients;
    private final CreateAndLoginUser createAndLoginUser;

    public TestPOMCreateOrder(String[] ingredients, CreateAndLoginUser createAndLoginUser) {
        this.ingredients = ingredients;
        this.createAndLoginUser = createAndLoginUser;
    }
    @Step("Создание заказа")
    public Response createOrder() {
        CreateOrderDTO obj = new CreateOrderDTO(ingredients);

        return given()
                .header("Content-type", "application/json")
                .spec(SetUp.requestSpec)
                .body(obj)
                .when()
                .post(Endpoints.CREATE_ORDER);
    }


    @Override
    @Step("Создание пользователя")
    public Response createUser() {
        return createAndLoginUser.createUser();
    }

    @Override
    @Step("Вход пользователя")
    public Response loginUser() {
        return createAndLoginUser.loginUser();
    }
}
