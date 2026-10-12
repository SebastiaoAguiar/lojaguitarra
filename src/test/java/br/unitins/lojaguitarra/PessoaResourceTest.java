package br.unitins.lojaguitarra;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class PessoaResourceTest {

    @Test
    void testListarClientesComEnderecos() {
        given()
          .when().get("/clientes/1")
          .then()
             .statusCode(200)
             .body("nome", is("Ana Clara Souza"))
             .body("enderecos.size()", is(2))
             .body("enderecos[0].municipio.estado.sigla", is("TO"));
    }

    @Test
    void testListarLuthiers() {
        given()
          .when().get("/luthiers")
          .then()
             .statusCode(200)
             .body("size()", is(2));
    }

    @Test
    void testCpfDuplicadoEntrePessoas() {
        // CPF de um luthier nao pode ser usado para cadastrar um cliente
        String json = """
            {"nome":"Teste","cpf":"98765432101","email":"teste@email.com","telefone":"63999999999",
             "enderecos":[{"logradouro":"Rua A","numero":"1","bairro":"Centro","cep":"77000000","idMunicipio":1}]}
            """;
        given().contentType(ContentType.JSON).body(json)
          .when().post("/clientes")
          .then()
             .statusCode(409);
    }

    @Test
    void testNaoExcluiMunicipioComEndereco() {
        given()
          .when().delete("/municipios/1")
          .then()
             .statusCode(409);
    }

    @Test
    void testNaoExcluiEstadoComMunicipios() {
        given()
          .when().delete("/estados/1")
          .then()
             .statusCode(409);
    }
}
