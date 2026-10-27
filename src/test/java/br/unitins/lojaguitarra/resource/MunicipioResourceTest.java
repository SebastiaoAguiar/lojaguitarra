package br.unitins.lojaguitarra.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MunicipioResourceTest {

    @Test
    void listarEBuscarPorNomeRetornaMunicipiosComEstado() {
        given()
                .when().get("/municipios")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Palmas"));

        given()
                .queryParam("nome", "PALM")
                .when().get("/municipios/search")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Palmas"))
                .body("estado.sigla", hasItem("TO"));
    }

    @Test
    void criarAtualizarEBuscarMunicipio() {
        String nome = nomeUnico("Municipio");
        Long id = criarMunicipio(nome);

        try {
            String nomeAtualizado = nome + " atualizado";
            given()
                    .contentType("application/json")
                    .body(Map.of("nome", nomeAtualizado, "idEstado", 2))
                    .when().put("/municipios/" + id)
                    .then()
                    .statusCode(204);

            given()
                    .when().get("/municipios/" + id)
                    .then()
                    .statusCode(200)
                    .body("nome", equalTo(nomeAtualizado))
                    .body("estado.sigla", equalTo("GO"));
        } finally {
            excluirMunicipio(id);
        }
    }

    @Test
    void rejeitarDadosInvalidos() {
        given()
                .contentType("application/json")
                .body(Map.of("nome", " ", "idEstado", -1))
                .when().post("/municipios")
                .then()
                .statusCode(400);
    }

    @Test
    void rejeitarIdsInexistentes() {
        given()
                .when().get("/municipios/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .contentType("application/json")
                .body(Map.of("nome", "Nome valido", "idEstado", 1))
                .when().put("/municipios/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .when().delete("/municipios/9223372036854775807")
                .then()
                .statusCode(404);
    }

    @Test
    void naoExcluiMunicipioComEndereco() {
        given()
                .when().delete("/municipios/1")
                .then()
                .statusCode(409);
    }

    @Test
    void excluirMunicipioCriadoRetorna204() {
        Long id = criarMunicipio(nomeUnico("Municipio para excluir"));

        given()
                .when().delete("/municipios/" + id)
                .then()
                .statusCode(204);

        given()
                .when().get("/municipios/" + id)
                .then()
                .statusCode(404);
    }

    private Long criarMunicipio(String nome) {
        Object id = given()
                .contentType("application/json")
                .body(Map.of("nome", nome, "idEstado", 1))
                .when().post("/municipios")
                .then()
                .statusCode(201)
                .body("nome", equalTo(nome))
                .body("estado.sigla", equalTo("TO"))
                .extract()
                .path("id");
        return ((Number) id).longValue();
    }

    private void excluirMunicipio(Long id) {
        given().when().delete("/municipios/" + id);
    }

    private String nomeUnico(String prefixo) {
        return prefixo + " " + System.nanoTime();
    }
}
