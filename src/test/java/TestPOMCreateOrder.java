import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TestPOMCreateOrder implements CreateAndLoginUser {
    private final String[] ingredients;
    private final CreateAndLoginUser createAndLoginUser;

    public TestPOMCreateOrder(String[] ingredients, CreateAndLoginUser createAndLoginUser) {
        this.ingredients = ingredients;
        this.createAndLoginUser = createAndLoginUser;
    }

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
    public Response createUser() {
        return createAndLoginUser.createUser();
    }

    @Override
    public Response loginUser() {
        return createAndLoginUser.loginUser();
    }
}
