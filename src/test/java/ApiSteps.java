import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.Assertion;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.*;

public class ApiSteps {
    @Step("Checking if pictures are correct")
    public static void checkPics(Map<String, String> expectedResults) {
        Response response =
                given()
                        .when()
                        .get("api/users?page=2")
                        .then()
                        .body("data.id", not(hasItem(nullValue())))
                        .body("data.avatar", not(hasItem(nullValue())))
                        .extract().response();
        JsonPath jpath = response.jsonPath();
        List<Integer> ids = jpath.getList("data.id");
        List<String> avatars = jpath.getList("data.avatar");
        Assert.assertEquals(avatars.size(), expectedResults.size());

        for (int i = 0; i < ids.size(); i++) {
            if (!expectedResults.get(ids.get(i).toString()).equals(avatars.get(i))) {
                Assert.assertTrue(false, "ID " + ids.get(i) + " has wrong avatar");
            }
        }
        Assert.assertTrue(true);
    }

    @Step("Registration check")
    public static void userRegistration(Map<String,String>data) {

        Response response = given()
                .body(data)
                .when()
                .post("api/register")
                .then()
                .log().all()
                .body("id", not(hasItem(nullValue())))
                .body("token", not(hasItem(nullValue())))
                .extract().response();
        Assert.assertTrue(response.statusCode() == 200);


    }

    @Step("Login successful check")
    public static void successfulLogin(Map<String,String>data) {

        Response response = given()
                .body(data)
                .when()
                .post("/api/login")
                .then()
                .log().all()
                .extract().response();
        JsonPath jPath = response.jsonPath();
        Assert.assertTrue(jPath.get("token").equals("QpwL5tke4Pnpja7X4"));


    }

    @Step("Login unsuccessful check")
    public static void unsuccessfulLogin(Map<String,String>data) {
        Response response = given()
                .body(data)
                .when()
                .post("/api/login")
                .then()
                .log().all()
                .statusCode(400)
                .extract().response();
        JsonPath jPath = response.jsonPath();
        Assert.assertTrue(jPath.get("error").equals("Missing password"));


    }

    @Step("Check that data sorted by year")
    public static void checkDataSorting() {

        Response response =
                given()
                        .when()
                        .get("api/unknown")
                        .then()
                        .log().all()
                        .extract().response();
        JsonPath jpath = response.jsonPath();
        List<Integer> ids = jpath.getList("data.year");
        List<Integer> sorted = new ArrayList<>();
        sorted.addAll(ids);
        Collections.sort(sorted);
        for (int i = 0; i < ids.size(); i++) {
            if (!ids.get(i).equals(sorted.get(i))) {
                Assert.assertTrue(false);
            }
        }
        Assert.assertTrue(true);


    }

}
