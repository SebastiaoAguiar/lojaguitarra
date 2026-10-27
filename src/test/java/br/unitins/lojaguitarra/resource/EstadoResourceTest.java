package br.unitins.lojaguitarra.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class EstadoResourceTest {

    @Test
    void listarEBuscarPorNomeRetornaEstados() {
        given()
                .when().get("/estados")
                .then()
                .statusCode(200)
                .body("sigla", hasItem("TO"));

        given()
                .queryParam("nome", "GOI")
                .when().get("/estados/search")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Goias"));
    }

    @Test
    void criarAtualizarEBuscarEstado() {
        String nome = nomeUnico("Estado");
        String sigla = siglaUnica();
        Long id = criarEstado(nome, sigla);

        try {
            String nomeAtualizado = nome + " atualizado";
            given()
                    .contentType("application/json")
                    .body(Map.of("nome", nomeAtualizado, "sigla", sigla, "idRegiao", 3))
                    .when().put("/estados/" + id)
                    .then()
                    .statusCode(204);

            given()
                    .when().get("/estados/" + id)
                    .then()
                    .statusCode(200)
                    .body("nome", equalTo(nomeAtualizado))
                    .body("sigla", equalTo(sigla));
        } finally {
            excluirEstado(id);
        }
    }

    @Test
    void rejeitarDadosInvalidos() {
        given()
                .contentType("application/json")
                .body(Map.of("nome", " ", "sigla", "x1", "idRegiao", 8))
                .when().post("/estados")
                .then()
                .statusCode(400);
    }

    @Test
    void rejeitarIdsInexistentes() {
        given()
                .when().get("/estados/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .contentType("application/json")
                .body(Map.of("nome", "Nome valido", "sigla", siglaUnica(), "idRegiao", 3))
                .when().put("/estados/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .when().delete("/estados/9223372036854775807")
                .then()
                .statusCode(404);
    }

    @Test
    void naoExcluiEstadoComMunicipios() {
        given()
                .when().delete("/estados/1")
                .then()
                .statusCode(409);
    }

    @Test
    void excluirEstadoCriadoRetorna204() {
        Long id = criarEstado(nomeUnico("Estado para excluir"), siglaUnica());

        given()
                .when().delete("/estados/" + id)
                .then()
                .statusCode(204);

        given()
                .when().get("/estados/" + id)
                .then()
                .statusCode(404);
    }

    private Long criarEstado(String nome, String sigla) {
        Object id = given()
                .contentType("application/json")
                .body(Map.of("nome", nome, "sigla", sigla, "idRegiao", 3))
                .when().post("/estados")
                .then()
                .statusCode(201)
                .body("nome", equalTo(nome))
                .body("sigla", equalTo(sigla))
                .extract()
                .path("id");
        return ((Number) id).longValue();
    }

    private void excluirEstado(Long id) {
        given().when().delete("/estados/" + id);
    }

    private String nomeUnico(String prefixo) {
        return prefixo + " " + System.nanoTime();
    }

    private String siglaUnica() {
        long value = Math.floorMod(System.nanoTime(), 676);
        char primeira = (char) ('A' + value / 26);
        char segunda = (char) ('A' + value % 26);
        String sigla = "" + primeira + segunda;
        if (sigla.equals("TO") || sigla.equals("GO") || sigla.equals("RJ") || sigla.equals("SP")
                || sigla.equals("BA") || sigla.equals("PR")) {
            return "QZ";
        }
        return sigla;
    }
}
