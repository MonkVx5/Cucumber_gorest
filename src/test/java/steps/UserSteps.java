package steps;

import config.RestAssuredConfig;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.model.User;
import org.junit.Assert;

import java.util.*;

import static org.hamcrest.Matchers.*;

public class UserSteps {

    private RequestSpecification request;
    private Response response;
    private User user;
    private List<User> users;
    private Integer wantedId;

    @Given("a new user with name {string}, email {string}, gender {string}, and status {string}")
    public void prepareNewUser(String name, String email, String gender, String status) {
        request = RestAssuredConfig.getRequestSpecification();
        user = new User();
        user.setName(name);
        user.setGender(gender);
        user.setStatus(status);
        if(email.contains("timestamp")) {
            user.setEmail(generateRandomEmail(email));
        } else {
            user.setEmail(email);
        }
    }

    private String generateRandomEmail(String baseEmail) {
        String uniqueId = UUID.randomUUID().toString();
        return baseEmail.replace("timestamp", uniqueId);
    }

    @When("the user is created")
    public void postNewUser() {
        response = request.body(user).when().post("/users");
    }

    @Then("the user creation should be successful")
    public void checkResponseBody() {
        User userResponse = response.then().statusCode(201).body("name", equalTo(user.getName())).extract().body().as(User.class);

        Assert.assertEquals("User name", user.getName(), userResponse.getName());
        Assert.assertEquals("User email", user.getEmail(), userResponse.getEmail());
        Assert.assertEquals("User gender", user.getGender(), userResponse.getGender());
        Assert.assertEquals("User status", user.getStatus(), userResponse.getStatus());
    }

    @Then("the user should exist in the system")
    public void checkIfUserExistsInSystem() {
        int userId = response.jsonPath().getInt("id");

        User userInSystem = request.when()
                .get("/users/" + userId)
                .then()
                .statusCode(200).body("id", equalTo(userId)).extract().body().as(User.class);

        Assert.assertEquals("User name", user.getName(), userInSystem.getName());
        Assert.assertEquals("User email", user.getEmail(), userInSystem.getEmail());
        Assert.assertEquals("User gender", user.getGender(), userInSystem.getGender());
        Assert.assertEquals("User status", user.getStatus(), userInSystem.getStatus());
    }

    @Then("the user creation should fail with status {int}")
    public void checkIfFailStatus(int expectedStatusCode) {
        int responseCode = response.statusCode();
        Assert.assertEquals("Fail status code", expectedStatusCode, responseCode);
    }

    @Then("the user creation should fail with message {string}")
    public void checkIfFailStatus(String expectedMessage) {
        String message = response.jsonPath().get("[0].message");
        Assert.assertEquals( "Response message", expectedMessage, message);
    }

    @Given("get list of all users")
    public void getListOfAllUsers() {
        request = RestAssuredConfig.getRequestSpecification();
        users = Arrays.asList(request.when()
                .get("/users").getBody().as(User[].class));
    }

    @When("find id of user with email {string}")
    public void findIdOfUserWithEmail(String email) {
        try {
            wantedId = users.stream()
                    .filter(user -> email.equals(user.getEmail()))
                    .findFirst().get().getId();
        } catch (NoSuchElementException e) {
            System.out.println(e);
        }
        Assert.assertNotNull("Wanted id is null. Could not find userId for email: " + email, wantedId);
    }

    @Then("the user should deleted with status {int}")
    public void deleteTheUser(int expectedCode) {
        int actualCode = request.when().delete("/users/" + wantedId).statusCode();
        Assert.assertEquals("Delete user: status code", expectedCode, actualCode);
    }

    @When("the user is created without token")
    public void tryCreateUserWithoutToken() {
        response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post("/users");
    }

}