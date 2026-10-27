package br.unitins.lojaguitarra.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class GuitarraEletricaResourceTest {

    @Test
    void listarEBuscarRetornaGuitarrasEletricas() {
        given()
                .when().get("/guitarras-eletricas")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Stratocaster"));

        given()
                .queryParam("nome", "STRATO")
                .when().get("/guitarras-eletricas/search")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Stratocaster"));

        given()
                .when().get("/guitarras-eletricas/nome/strato")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Stratocaster"));

        given()
                .when().get("/guitarras-eletricas/ponte/FLOYD_ROSE")
                .then()
                .statusCode(200)
                .body("nome", hasItem("RG550"));
    }

    @Test
    void criarAtualizarEBuscarGuitarraEletrica() {
        Map<String, Object> guitarra = dadosGuitarra("Eletrica");
        Long id = criarGuitarra(guitarra);

        try {
            Map<String, Object> atualizada = dadosGuitarra("Eletrica atualizada");
            atualizada.put("tipoPonte", "TREMOLO");
            given()
                    .contentType("application/json")
                    .body(atualizada)
                    .when().put("/guitarras-eletricas/" + id)
                    .then()
                    .statusCode(204);

            given()
                    .when().get("/guitarras-eletricas/" + id)
                    .then()
                    .statusCode(200)
                    .body("nome", equalTo(atualizada.get("nome")))
                    .body("tipoPonte", equalTo("TREMOLO"))
                    .body("captadores.size()", equalTo(1));
        } finally {
            excluirGuitarra(id);
        }
    }

    @Test
    void rejeitarDadosInvalidos() {
        given()
                .contentType("application/json")
                .body(Map.of("nome", " ", "idMarca", -1, "preco", 0))
                .when().post("/guitarras-eletricas")
                .then()
                .statusCode(400);
    }

    @Test
    void rejeitarGuitarraSemCaptador() {
        Map<String, Object> guitarra = dadosGuitarra("Sem captador");
        guitarra.put("idsCaptadores", List.of());

        given()
                .contentType("application/json")
                .body(guitarra)
                .when().post("/guitarras-eletricas")
                .then()
                .statusCode(400);
    }

    @Test
    void rejeitarIdsInexistentes() {
        given()
                .when().get("/guitarras-eletricas/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .contentType("application/json")
                .body(dadosGuitarra("Inexistente"))
                .when().put("/guitarras-eletricas/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .when().delete("/guitarras-eletricas/9223372036854775807")
                .then()
                .statusCode(404);
    }

    @Test
    void excluirGuitarraEletricaCriadaRetorna204() {
        Long id = criarGuitarra(dadosGuitarra("Para excluir"));

        given()
                .when().delete("/guitarras-eletricas/" + id)
                .then()
                .statusCode(204);

        given()
                .when().get("/guitarras-eletricas/" + id)
                .then()
                .statusCode(404);
    }

    private Long criarGuitarra(Map<String, Object> guitarra) {
        Object id = given()
                .contentType("application/json")
                .body(guitarra)
                .when().post("/guitarras-eletricas")
                .then()
                .statusCode(201)
                .body("nome", equalTo(guitarra.get("nome")))
                .body("marca.nome", equalTo("Fender"))
                .body("captadores.size()", equalTo(1))
                .extract()
                .path("id");
        return ((Number) id).longValue();
    }

    private Map<String, Object> dadosGuitarra(String prefixo) {
        Map<String, Object> especificacao = Map.of(
                "formatoCorpo", "Strat",
                "madeiraCorpo", "Amieiro",
                "madeiraEscala", "Maple",
                "numeroTrastes", 21);
        Map<String, Object> guitarra = new HashMap<>();
        guitarra.put("nome", prefixo + " " + System.nanoTime());
        guitarra.put("idMarca", 1);
        guitarra.put("modelo", "Modelo teste");
        guitarra.put("cor", "Preta");
        guitarra.put("preco", 1500.0);
        guitarra.put("especificacao", especificacao);
        guitarra.put("configuracaoCaptadores", "SSS");
        guitarra.put("captacaoAtiva", false);
        guitarra.put("tipoPonte", "FIXA");
        guitarra.put("idsCaptadores", List.of(1));
        return guitarra;
    }

    private void excluirGuitarra(Long id) {
        given().when().delete("/guitarras-eletricas/" + id);
    }
}
