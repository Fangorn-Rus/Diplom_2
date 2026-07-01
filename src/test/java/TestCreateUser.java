
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestCreateUser {
    TestPOMCreateUser obj;

    @Before
    public void init() {
        SetUp.setUp();
    }

    @Test
    public void testCreateUser(){
        obj = new TestPOMCreateUser("test_testCreateUser222@mail.ru", "12345", "Vladimir");
        obj.createUser()
                .then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
        ;
    }

    @Test
    public void testReCreateUser(){
        obj = new TestPOMCreateUser("test_testReCreateUser222@mail.ru", "12345", "Vladimir");
        obj.reCreateUser()
                .then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
        ;
    }

    @Test
    public void testCreateUserWithoutField(){
        obj = new TestPOMCreateUser("test_testCreateUserWithoutField2222@mail.ru", "12345", null);
        obj.createUser()
                .then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
        ;
    }
}
