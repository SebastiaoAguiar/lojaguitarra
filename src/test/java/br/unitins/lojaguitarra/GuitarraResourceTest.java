package br.unitins.lojaguitarra;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class GuitarraResourceTest {

    @Test
    void testListarGuitarras() {
        given()
          .when().get("/guitarras")
          .then()
             .statusCode(200)
             .body("size()", is(13));
    }

    @Test
    void testListarGuitarrasEletricas() {
        given()
          .when().get("/guitarras-eletricas")
          .then()
             .statusCode(200)
             .body("size()", is(8));
    }

    @Test
    void testListarGuitarrasAcusticas() {
        given()
          .when().get("/guitarras-acusticas")
          .then()
             .statusCode(200)
             .body("size()", is(2));
    }

    @Test
    void testListarGuitarrasEletroacusticas() {
        given()
          .when().get("/guitarras-eletroacusticas")
          .then()
             .statusCode(200)
             .body("size()", is(3));
    }

    @Test
    void testBuscarGuitarraPolimorficaRetornaTipo() {
        given()
          .when().get("/guitarras/9")
          .then()
             .statusCode(200)
             .body("tipo", is("ACUSTICA"));
    }

    @Test
    void testGuitarraEletricaTrazEspecificacaoECaptadores() {
        given()
          .when().get("/guitarras-eletricas/2")
          .then()
             .statusCode(200)
             .body("marca.nome", is("Gibson"))
             .body("especificacao.formatoCorpo", is("Les Paul"))
             .body("captadores.size()", is(2));
    }

    @Test
    void testListagemPolimorficaTrazCaptadoresSoDasEletricas() {
        given().when().get("/guitarras/2").then()
             .body("tipo", is("ELETRICA"))
             .body("captadores.size()", is(2));
        given().when().get("/guitarras/9").then()
             .body("especificacao.formatoCorpo", is("Dreadnought"))
             .body("captadores.size()", is(0));
    }

    @Test
    void testBuscasPolimorficas() {
        given().when().get("/guitarras/search/marca?nome=fender").then()
             .statusCode(200)
             .body("size()", is(3));
        given().when().get("/guitarras/marca/fender").then()
             .statusCode(200)
             .body("size()", is(3));
    }

    @Test
    void testCadastrarGuitarraAcustica() {
        String json = """
            {"nome":"Teste Acustica","idMarca":6,"modelo":"Modelo X","cor":"Natural","preco":1500,
             "especificacao":{"formatoCorpo":"Jumbo","madeiraCorpo":"Abeto","madeiraEscala":"Ebano","numeroTrastes":20},
             "tipoTampo":"MACICO","tipoCorda":"ACO","cutaway":true}
            """;
        Integer id = given().contentType(ContentType.JSON).body(json)
          .when().post("/guitarras-acusticas")
          .then()
             .statusCode(201)
             .body("especificacao.formatoCorpo", is("Jumbo"))
             .body("tipoCorda", is("ACO"))
             .extract().path("id");

        // apaga o que criou para nao alterar a contagem dos outros testes
        given().when().delete("/guitarras-acusticas/" + id).then().statusCode(204);
    }

    @Test
    void testEletricaExigeCaptador() {
        String json = """
            {"nome":"Sem Captador","idMarca":1,"modelo":"Modelo X","cor":"Preta","preco":1500,
             "especificacao":{"formatoCorpo":"Strat","madeiraCorpo":"Amieiro","madeiraEscala":"Maple","numeroTrastes":21},
             "configuracaoCaptadores":"SSS","captacaoAtiva":false,"tipoPonte":"FIXA","idsCaptadores":[]}
            """;
        given().contentType(ContentType.JSON).body(json)
          .when().post("/guitarras-eletricas")
          .then()
             .statusCode(400);
    }

    @Test
    void testNaoExcluiMarcaComGuitarras() {
        given()
          .when().delete("/marcas/1")
          .then()
             .statusCode(409);
    }

    @Test
    void testNaoExcluiCaptadorEmUso() {
        given()
          .when().delete("/captadores/1")
          .then()
             .statusCode(409);
    }
}
