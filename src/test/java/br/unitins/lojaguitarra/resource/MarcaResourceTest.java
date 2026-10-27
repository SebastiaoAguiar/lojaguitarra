package br.unitins.lojaguitarra.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MarcaResourceTest {

    @Test
    void listarEBuscarPorNomeRetornaMarcas() {
        given()
                .when().get("/marcas")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Fender"));

        given()
                .queryParam("nome", "FEND")
                .when().get("/marcas/search")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Fender"));
    }

    @Test
    void criarAtualizarEBuscarMarca() {
        Long id = criarMarca(dadosMarca("Marca"));

        try {
            Map<String, String> atualizada = dadosMarca("Marca atualizada");
            given()
                    .contentType("application/json")
                    .body(atualizada)
                    .when().put("/marcas/" + id)
                    .then()
                    .statusCode(204);

            given()
                    .when().get("/marcas/" + id)
                    .then()
                    .statusCode(200)
                    .body("nome", equalTo(atualizada.get("nome")))
                    .body("paisOrigem", equalTo(atualizada.get("paisOrigem")));
        } finally {
            excluirMarca(id);
        }
    }

    @Test
    void rejeitarDadosInvalidos() {
        given()
                .contentType("application/json")
                .body(Map.of("nome", " ", "paisOrigem", ""))
                .when().post("/marcas")
                .then()
                .statusCode(400);
    }

    @Test
    void rejeitarIdsInexistentes() {
        given()
                .when().get("/marcas/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .contentType("application/json")
                .body(dadosMarca("Marca inexistente"))
                .when().put("/marcas/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .when().delete("/marcas/9223372036854775807")
                .then()
                .statusCode(404);
    }

    @Test
    void naoExcluiMarcaComGuitarras() {
        given()
                .when().delete("/marcas/1")
                .then()
                .statusCode(409);
    }

    @Test
    void excluirMarcaCriadaRetorna204() {
        Long id = criarMarca(dadosMarca("Marca para excluir"));

        given()
                .when().delete("/marcas/" + id)
                .then()
                .statusCode(204);

        given()
                .when().get("/marcas/" + id)
                .then()
                .statusCode(404);
    }

    private Long criarMarca(Map<String, String> marca) {
        Object id = given()
                .contentType("application/json")
                .body(marca)
                .when().post("/marcas")
                .then()
                .statusCode(201)
                .body("nome", equalTo(marca.get("nome")))
                .extract()
                .path("id");
        return ((Number) id).longValue();
    }

    private Map<String, String> dadosMarca(String prefixo) {
        return Map.of(
                "nome", prefixo + " " + System.nanoTime(),
                "paisOrigem", "Brasil");
    }

    private void excluirMarca(Long id) {
        given().when().delete("/marcas/" + id);
    }
}
