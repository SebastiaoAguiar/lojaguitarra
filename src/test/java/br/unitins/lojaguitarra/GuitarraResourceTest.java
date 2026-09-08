package br.unitins.lojaguitarra;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;

@QuarkusTest
class GuitarraResourceTest {
    @Test
    void testListarGuitarras() {
        given()
          .when().get("/guitarras")
          .then()
             .statusCode(200)
             .body("size()", is(8));
    }

}
