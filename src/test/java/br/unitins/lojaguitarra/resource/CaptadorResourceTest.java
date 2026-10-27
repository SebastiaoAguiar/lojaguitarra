package br.unitins.lojaguitarra.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class CaptadorResourceTest {

    @Test
    void listarEBuscarPorNomeRetornaCaptadores() {
        given()
                .when().get("/captadores")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Gibson P-90"));

        given()
                .queryParam("nome", "p-90")
                .when().get("/captadores/search")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Gibson P-90"));
    }

    @Test
    void criarAtualizarEBuscarCaptador() {
        Long id = criarCaptador(dadosCaptador("Captador"));

        try {
            Map<String, String> atualizado = dadosCaptador("Captador atualizado");
            given()
                    .contentType("application/json")
                    .body(atualizado)
                    .when().put("/captadores/" + id)
                    .then()
                    .statusCode(204);

            given()
                    .when().get("/captadores/" + id)
                    .then()
                    .statusCode(200)
                    .body("nome", equalTo(atualizado.get("nome")))
                    .body("tipo", equalTo(atualizado.get("tipo")));
        } finally {
            excluirCaptador(id);
        }
    }

    @Test
    void rejeitarDadosInvalidos() {
        given()
                .contentType("application/json")
                .body(Map.of("nome", " ", "tipo", ""))
                .when().post("/captadores")
                .then()
                .statusCode(400);
    }

    @Test
    void rejeitarIdsInexistentes() {
        given()
                .when().get("/captadores/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .contentType("application/json")
                .body(dadosCaptador("Captador inexistente"))
                .when().put("/captadores/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .when().delete("/captadores/9223372036854775807")
                .then()
                .statusCode(404);
    }

    @Test
    void naoExcluiCaptadorEmUso() {
        given()
                .when().delete("/captadores/1")
                .then()
                .statusCode(409);
    }

    @Test
    void excluirCaptadorCriadoRetorna204() {
        Long id = criarCaptador(dadosCaptador("Captador para excluir"));

        given()
                .when().delete("/captadores/" + id)
                .then()
                .statusCode(204);

        given()
                .when().get("/captadores/" + id)
                .then()
                .statusCode(404);
    }

    private Long criarCaptador(Map<String, String> captador) {
        Object id = given()
                .contentType("application/json")
                .body(captador)
                .when().post("/captadores")
                .then()
                .statusCode(201)
                .body("nome", equalTo(captador.get("nome")))
                .extract()
                .path("id");
        return ((Number) id).longValue();
    }

    private Map<String, String> dadosCaptador(String prefixo) {
        return Map.of(
                "nome", prefixo + " " + System.nanoTime(),
                "tipo", "Humbucker");
    }

    private void excluirCaptador(Long id) {
        given().when().delete("/captadores/" + id);
    }
}
