package br.unitins.lojaguitarra;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

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

    @Test
    void testGuitarraTrazEspecificacaoECaptadores() {
        given()
          .when().get("/guitarras/2")
          .then()
             .statusCode(200)
             .body("marca.nome", is("Gibson"))
             .body("especificacao.formatoCorpo", is("Les Paul"))
             .body("captadores.size()", is(2));
    }

    @Test
    void testGuitarraInexistente() {
        given()
          .when().get("/guitarras/9999")
          .then()
             .statusCode(404);
    }

    @Test
    void testNaoExcluiMarcaComGuitarras() {
        given()
          .when().delete("/marcas/1")
          .then()
             .statusCode(409);
    }
}
