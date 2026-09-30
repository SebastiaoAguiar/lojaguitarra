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
             .body("size()", is(13));
    }

    @Test
    void testListarGuitarrasEletricas() {
        given()
          .when().get("/guitarras-eletricas")
          .then()
             .statusCode(200)
             .body("size()", is(8));
    }

    @Test
    void testListarGuitarrasAcusticas() {
        given()
          .when().get("/guitarras-acusticas")
          .then()
             .statusCode(200)
             .body("size()", is(2));
    }

    @Test
    void testListarGuitarrasEletroacusticas() {
        given()
          .when().get("/guitarras-eletroacusticas")
          .then()
             .statusCode(200)
             .body("size()", is(3));
    }

    @Test
    void testBuscarGuitarraPolimorficaRetornaTipo() {
        given()
          .when().get("/guitarras/9")
          .then()
             .statusCode(200)
             .body("tipo", is("ACUSTICA"));
    }
}
