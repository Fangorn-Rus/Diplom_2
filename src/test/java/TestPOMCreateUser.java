import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TestPOMCreateUser {
    private final String email;
    private final String password;
    private final String name;
    CreateUserDTO obj;
    String accessToken;

    public TestPOMCreateUser(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    private static void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .spec(SetUp.requestSpec)
                .when()
                .delete(Endpoints.DELETE_USER)
                ;
    }

    public Response createUser() {
         obj = new CreateUserDTO(email, password, name);

        Response response = given()
                .header("Content-type", "application/json")
                .spec(SetUp.requestSpec)
                .body(obj)
                .when()
                .post(Endpoints.CREATE_USER);

        accessToken = response.path("accessToken");
        if(accessToken != null){
            deleteUser(accessToken);
        }
        return response;
    }

    public Response reCreateUser() {
        obj = new CreateUserDTO(email, password, name);

        Response response = given()
                .header("Content-type", "application/json")
                .spec(SetUp.requestSpec)
                .body(obj)
                .when()
                .post(Endpoints.CREATE_USER);

        Response response1 = given()
                .header("Content-type", "application/json")
                .spec(SetUp.requestSpec)
                .body(obj)
                .when()
                .post(Endpoints.CREATE_USER);

        accessToken = response.path("accessToken");
        if(accessToken != null){
            deleteUser(accessToken);
        }
        return response1;
    }





}
