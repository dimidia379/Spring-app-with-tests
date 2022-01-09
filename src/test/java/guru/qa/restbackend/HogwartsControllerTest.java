package guru.qa.restbackend;

import guru.qa.restbackend.domain.StudentInfo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;


public class HogwartsControllerTest {

    static {
        RestAssured.baseURI = "http://localhost:8080";
    }

    private RequestSpecification spec =
            with()
                    .baseUri("http://localhost:8080")
                    .basePath("/");

    private void createStudent(String name, String house) {
        String data = "{ \"student_name\":\"" + name + "\", \"student_house\":\"" + house + "\"}";

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when()
                .post("student")
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    void addNewStudent() {
        String data = "{ \"student_name\": \"Ginny Weasley\", \"student_house\": \"Gryffindor\" }";
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when()
                .post("student")
                .then()
                .log().all()
                .statusCode(201)
                .body("student_name", is("Ginny Weasley"))
                .body("student_house", is("Gryffindor"));
    }

    @Test
    void getAllStudents() {
        createStudent("James Potter", "Gryffindor");
        List<StudentInfo> students = given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("students/all")
                .then()
                .log().all()
                .statusCode(200)
                .extract().jsonPath().getList(".", StudentInfo.class);
        assertThat(students.size(), Matchers.greaterThan(0));
    }

    @Test
    void getStudentsByHouse() {
        createStudent("Harry Potter", "Gryffindor");
        createStudent("Draco Malfoy", "Slytherin");
        List<StudentInfo> students = given()
                .params("housename", "Gryffindor")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("students/house")
                .then()
                .log().body()
                .statusCode(200)
                .body("student_name.flatten()", hasItem("Harry Potter"))
                .extract().jsonPath().getList(".", StudentInfo.class);
        assertThat(students.size(), Matchers.greaterThan(0));
    }

    @Test
    void searchStudentByName() {
        createStudent("Hermione Granger", "Gryffindor");
        createStudent("Ron Weasley", "Gryffindor");
        List<StudentInfo> students = given()
                .params("name", "Hermione")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("students/search")
                .then()
                .log().body()
                .body("student_name.flatten()", hasItem("Hermione Granger"))
                .extract().jsonPath().getList(".", StudentInfo.class);
        assertThat(students.size(), Matchers.greaterThan(0));
    }
}
