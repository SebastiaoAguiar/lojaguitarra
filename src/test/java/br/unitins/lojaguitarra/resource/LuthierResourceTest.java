package br.unitins.lojaguitarra.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class LuthierResourceTest {

    @Test
    void listarEBuscarPorNomeEEspecialidadeRetornaLuthiers() {
        given()
                .when().get("/luthiers")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Diego Pereira Rocha"));

        given()
                .queryParam("nome", "DIEGO")
                .when().get("/luthiers/search")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Diego Pereira Rocha"));

        given()
                .queryParam("especialidade", "CAPTADORES")
                .when().get("/luthiers/search/especialidade")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Elisa Gomes Santos"));
    }

    @Test
    void criarAtualizarEBuscarLuthier() {
        Long id = criarLuthier(dadosLuthier("Luthier"));

        try {
            Map<String, Object> atualizado = dadosLuthier("Luthier atualizado");
            given()
                    .contentType("application/json")
                    .body(atualizado)
                    .when().put("/luthiers/" + id)
                    .then()
                    .statusCode(204);

            given()
                    .when().get("/luthiers/" + id)
                    .then()
                    .statusCode(200)
                    .body("nome", equalTo(atualizado.get("nome")))
                    .body("anosExperiencia", equalTo(atualizado.get("anosExperiencia")));
        } finally {
            excluirLuthier(id);
        }
    }

    @Test
    void rejeitarDadosInvalidos() {
        given()
                .contentType("application/json")
                .body(Map.of("nome", " ", "cpf", "123", "email", "x", "especialidade", "",
                        "anosExperiencia", 99))
                .when().post("/luthiers")
                .then()
                .statusCode(400);
    }

    @Test
    void rejeitarCpfDuplicado() {
        Map<String, Object> luthier = dadosLuthier("Luthier cpf duplicado");
        luthier.put("cpf", "98765432101");

        given()
                .contentType("application/json")
                .body(luthier)
                .when().post("/luthiers")
                .then()
                .statusCode(409);
    }

    @Test
    void rejeitarIdsInexistentes() {
        given()
                .when().get("/luthiers/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .contentType("application/json")
                .body(dadosLuthier("Luthier inexistente"))
                .when().put("/luthiers/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .when().delete("/luthiers/9223372036854775807")
                .then()
                .statusCode(404);
    }

    @Test
    void excluirLuthierCriadoRetorna204() {
        Long id = criarLuthier(dadosLuthier("Luthier para excluir"));

        given()
                .when().delete("/luthiers/" + id)
                .then()
                .statusCode(204);

        given()
                .when().get("/luthiers/" + id)
                .then()
                .statusCode(404);
    }

    private Long criarLuthier(Map<String, Object> luthier) {
        Object id = given()
                .contentType("application/json")
                .body(luthier)
                .when().post("/luthiers")
                .then()
                .statusCode(201)
                .body("nome", equalTo(luthier.get("nome")))
                .extract()
                .path("id");
        return ((Number) id).longValue();
    }

    private Map<String, Object> dadosLuthier(String prefixo) {
        long unique = Math.abs(System.nanoTime());
        Map<String, Object> luthier = new HashMap<>();
        luthier.put("nome", prefixo + " " + unique);
        luthier.put("cpf", String.format("%011d", unique % 100_000_000_000L));
        luthier.put("email", "luthier" + unique + "@example.com");
        luthier.put("especialidade", "Regulagem");
        luthier.put("anosExperiencia", (int) (unique % 40));
        return luthier;
    }

    private void excluirLuthier(Long id) {
        given().when().delete("/luthiers/" + id);
    }
}
