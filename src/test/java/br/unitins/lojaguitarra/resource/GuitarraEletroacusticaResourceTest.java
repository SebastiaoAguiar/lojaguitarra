package br.unitins.lojaguitarra.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class GuitarraEletroacusticaResourceTest {

    @Test
    void listarEBuscarRetornaGuitarrasEletroacusticas() {
        given()
                .when().get("/guitarras-eletroacusticas")
                .then()
                .statusCode(200)
                .body("nome", hasItem("214ce"));

        given()
                .queryParam("nome", "APX")
                .when().get("/guitarras-eletroacusticas/search")
                .then()
                .statusCode(200)
                .body("nome", hasItem("APX600"));

        given()
                .when().get("/guitarras-eletroacusticas/nome/gd30")
                .then()
                .statusCode(200)
                .body("nome", hasItem("GD30CE"));

        given()
                .when().get("/guitarras-eletroacusticas/captacao/PIEZO")
                .then()
                .statusCode(200)
                .body("nome", hasItem("214ce"));
    }

    @Test
    void criarAtualizarEBuscarGuitarraEletroacustica() {
        Map<String, Object> guitarra = dadosGuitarra("Eletroacustica");
        Long id = criarGuitarra(guitarra);

        try {
            Map<String, Object> atualizada = dadosGuitarra("Eletroacustica atualizada");
            atualizada.put("bandasEqualizador", 5);
            given()
                    .contentType("application/json")
                    .body(atualizada)
                    .when().put("/guitarras-eletroacusticas/" + id)
                    .then()
                    .statusCode(204);

            given()
                    .when().get("/guitarras-eletroacusticas/" + id)
                    .then()
                    .statusCode(200)
                    .body("nome", equalTo(atualizada.get("nome")))
                    .body("bandasEqualizador", equalTo(5));
        } finally {
            excluirGuitarra(id);
        }
    }

    @Test
    void rejeitarDadosInvalidos() {
        Map<String, Object> guitarra = dadosGuitarra("Invalida");
        guitarra.put("nome", " ");
        guitarra.put("bandasEqualizador", 11);

        given()
                .contentType("application/json")
                .body(guitarra)
                .when().post("/guitarras-eletroacusticas")
                .then()
                .statusCode(400);
    }

    @Test
    void rejeitarIdsInexistentes() {
        given()
                .when().get("/guitarras-eletroacusticas/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .contentType("application/json")
                .body(dadosGuitarra("Inexistente"))
                .when().put("/guitarras-eletroacusticas/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .when().delete("/guitarras-eletroacusticas/9223372036854775807")
                .then()
                .statusCode(404);
    }

    @Test
    void excluirGuitarraEletroacusticaCriadaRetorna204() {
        Long id = criarGuitarra(dadosGuitarra("Para excluir"));

        given()
                .when().delete("/guitarras-eletroacusticas/" + id)
                .then()
                .statusCode(204);

        given()
                .when().get("/guitarras-eletroacusticas/" + id)
                .then()
                .statusCode(404);
    }

    private Long criarGuitarra(Map<String, Object> guitarra) {
        Object id = given()
                .contentType("application/json")
                .body(guitarra)
                .when().post("/guitarras-eletroacusticas")
                .then()
                .statusCode(201)
                .body("nome", equalTo(guitarra.get("nome")))
                .body("tipoCaptacao", equalTo("PIEZO"))
                .extract()
                .path("id");
        return ((Number) id).longValue();
    }

    private Map<String, Object> dadosGuitarra(String prefixo) {
        Map<String, Object> especificacao = Map.of(
                "formatoCorpo", "Thinline",
                "madeiraCorpo", "Meranti",
                "madeiraEscala", "Pau-ferro",
                "numeroTrastes", 20);
        Map<String, Object> guitarra = new HashMap<>();
        guitarra.put("nome", prefixo + " " + System.nanoTime());
        guitarra.put("idMarca", 7);
        guitarra.put("modelo", "Modelo teste");
        guitarra.put("cor", "Sunburst");
        guitarra.put("preco", 2500.0);
        guitarra.put("especificacao", especificacao);
        guitarra.put("tipoTampo", "LAMINADO");
        guitarra.put("cutaway", true);
        guitarra.put("tipoCaptacao", "PIEZO");
        guitarra.put("afinadorEmbutido", true);
        guitarra.put("bandasEqualizador", 3);
        return guitarra;
    }

    private void excluirGuitarra(Long id) {
        given().when().delete("/guitarras-eletroacusticas/" + id);
    }
}
