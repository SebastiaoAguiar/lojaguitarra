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
class ClienteResourceTest {

    @Test
    void listarEBuscarPorNomeRetornaClientesComEnderecos() {
        given()
                .when().get("/clientes")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Ana Clara Souza"));

        given()
                .queryParam("nome", "ANA")
                .when().get("/clientes/search")
                .then()
                .statusCode(200)
                .body("nome", hasItem("Ana Clara Souza"));

        given()
                .when().get("/clientes/1")
                .then()
                .statusCode(200)
                .body("enderecos.size()", equalTo(2))
                .body("enderecos[0].municipio.estado.sigla", equalTo("TO"));
    }

    @Test
    void criarAtualizarEBuscarCliente() {
        Map<String, Object> cliente = dadosCliente("Cliente");
        Long id = criarCliente(cliente);

        try {
            Map<String, Object> atualizado = dadosCliente("Cliente atualizado");
            atualizado.put("cpf", cliente.get("cpf"));
            given()
                    .contentType("application/json")
                    .body(atualizado)
                    .when().put("/clientes/" + id)
                    .then()
                    .statusCode(204);

            given()
                    .when().get("/clientes/" + id)
                    .then()
                    .statusCode(200)
                    .body("nome", equalTo(atualizado.get("nome")))
                    .body("enderecos.size()", equalTo(1));
        } finally {
            excluirCliente(id);
        }
    }

    @Test
    void rejeitarDadosInvalidos() {
        given()
                .contentType("application/json")
                .body(Map.of("nome", " ", "cpf", "123", "email", "x", "telefone", "1",
                        "enderecos", List.of()))
                .when().post("/clientes")
                .then()
                .statusCode(400);
    }

    @Test
    void rejeitarCpfDuplicadoEntrePessoas() {
        // CPF de um luthier nao pode ser usado para cadastrar um cliente
        Map<String, Object> cliente = dadosCliente("Cliente cpf duplicado");
        cliente.put("cpf", "98765432101");

        given()
                .contentType("application/json")
                .body(cliente)
                .when().post("/clientes")
                .then()
                .statusCode(409);
    }

    @Test
    void rejeitarIdsInexistentes() {
        given()
                .when().get("/clientes/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .contentType("application/json")
                .body(dadosCliente("Cliente inexistente"))
                .when().put("/clientes/9223372036854775807")
                .then()
                .statusCode(404);

        given()
                .when().delete("/clientes/9223372036854775807")
                .then()
                .statusCode(404);
    }

    @Test
    void excluirClienteCriadoRetorna204() {
        Long id = criarCliente(dadosCliente("Cliente para excluir"));

        given()
                .when().delete("/clientes/" + id)
                .then()
                .statusCode(204);

        given()
                .when().get("/clientes/" + id)
                .then()
                .statusCode(404);
    }

    private Long criarCliente(Map<String, Object> cliente) {
        Object id = given()
                .contentType("application/json")
                .body(cliente)
                .when().post("/clientes")
                .then()
                .statusCode(201)
                .body("nome", equalTo(cliente.get("nome")))
                .body("enderecos.size()", equalTo(1))
                .extract()
                .path("id");
        return ((Number) id).longValue();
    }

    private Map<String, Object> dadosCliente(String prefixo) {
        long unique = Math.abs(System.nanoTime());
        Map<String, Object> endereco = Map.of(
                "logradouro", "Rua Teste",
                "numero", "10",
                "bairro", "Centro",
                "cep", "77000000",
                "idMunicipio", 1);
        Map<String, Object> cliente = new HashMap<>();
        cliente.put("nome", prefixo + " " + unique);
        cliente.put("cpf", String.format("%011d", unique % 100_000_000_000L));
        cliente.put("email", "cliente" + unique + "@example.com");
        cliente.put("telefone", "63999999999");
        cliente.put("enderecos", List.of(endereco));
        return cliente;
    }

    private void excluirCliente(Long id) {
        given().when().delete("/clientes/" + id);
    }
}
