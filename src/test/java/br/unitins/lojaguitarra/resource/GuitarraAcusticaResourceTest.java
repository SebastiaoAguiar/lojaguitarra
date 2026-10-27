package br.unitins.lojaguitarra.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class GuitarraAcusticaResourceTest {

    @Test
    void listarEBuscarRetornaGuitarrasAcusticas() {
        given()
                .when().get("/guitarras-acusticas")
                .then()
                .statusCode(200)
                .body("nome", hasItem("D-28"));

        given()
                .queryParam("nome", "d-28")
                .when().get("/guitarras-acusticas/search")
                .then()
                .statusCode(200)
                .body("nome", hasItem("D-28"));

        given()
                .when().get("/guitarras-acusticas/nome/c40")
                .then()
                .statusCode(200)
                .body("nome", hasItem("C40"));

        given()
                .when().get("/guitarras-acusticas/corda/NYLON")
                .then()
                .statusCode(200)
                .body("nome", hasItem("C40"));
    }

    @Test
    void criarAtualizarEBuscarGuitarraAcustica() {
        Map<String, Object> guitarra = dadosGuitarra("Acustica");
        Long id = criarGuitarra(guitarra);

        try {
            Map<String, Object> atualizada = dadosGuitarra("Acustica atualizada");
            atualizada.put("tipoCorda", "NYLON");
            given()
                    .contentType("application/json")
                    .body(atualizada)
                    .when().put("/guitarras-acusticas/" + id)
                    .then()
                    .statusCode(204);

            given()
                    .when().get("/guitarras-acusticas/" + id)
                    .then()
                    .statusCode(200)
                    .body("nome", equalTo(atualizada.get("nome")))
                    .body("tipoCorda", equalTo("NYLON"));
        } finally {
            excluirGuitarra(id);
        }
    }

    @Test
    void rejeitarDadosInvalidos() {
        given()
                .contentType("application/json")
                .body(Map.of("nome", " ", "idMarca", -1, "preco", 0))
                .when().post("/guitarras-acusticas")
                .then()
                .statusCode(400);
    }

    @Test
    void rejeitarIdsInexistentes() {
        given()
                .when().get("/guitarras-acusticas/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .contentType("application/json")
                .body(dadosGuitarra("Inexistente"))
                .when().put("/guitarras-acusticas/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .when().delete("/guitarras-acusticas/9223372036854775807")
                .then()
                .statusCode(404);
    }

    @Test
    void excluirGuitarraAcusticaCriadaRetorna204() {
        Long id = criarGuitarra(dadosGuitarra("Para excluir"));

        given()
                .when().delete("/guitarras-acusticas/" + id)
                .then()
                .statusCode(204);

        given()
                .when().get("/guitarras-acusticas/" + id)
                .then()
                .statusCode(404);
    }

    private Long criarGuitarra(Map<String, Object> guitarra) {
        Object id = given()
                .contentType("application/json")
                .body(guitarra)
                .when().post("/guitarras-acusticas")
                .then()
                .statusCode(201)
                .body("nome", equalTo(guitarra.get("nome")))
                .body("especificacao.formatoCorpo", equalTo("Jumbo"))
                .extract()
                .path("id");
        return ((Number) id).longValue();
    }

    private Map<String, Object> dadosGuitarra(String prefixo) {
        Map<String, Object> especificacao = Map.of(
                "formatoCorpo", "Jumbo",
                "madeiraCorpo", "Abeto",
                "madeiraEscala", "Ebano",
                "numeroTrastes", 20);
        Map<String, Object> guitarra = new HashMap<>();
        guitarra.put("nome", prefixo + " " + System.nanoTime());
        guitarra.put("idMarca", 6);
        guitarra.put("modelo", "Modelo teste");
        guitarra.put("cor", "Natural");
        guitarra.put("preco", 1500.0);
        guitarra.put("especificacao", especificacao);
        guitarra.put("tipoTampo", "MACICO");
        guitarra.put("tipoCorda", "ACO");
        guitarra.put("cutaway", true);
        return guitarra;
    }

    private void excluirGuitarra(Long id) {
        given().when().delete("/guitarras-acusticas/" + id);
    }
}
