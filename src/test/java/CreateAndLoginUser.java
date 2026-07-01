import io.restassured.response.Response;

public interface CreateAndLoginUser {
    Response createUser();
    Response loginUser();
}
