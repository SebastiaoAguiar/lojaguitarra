package br.unitins.lojaguitarra.resource;

import br.unitins.lojaguitarra.dto.GuitarraResponseDTO;
import br.unitins.lojaguitarra.service.GuitarraService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

// listagem polimorfica: traz eletricas, acusticas e eletroacusticas juntas
// o cadastro, a edicao e a exclusao sao feitos nos resources de cada tipo
@Path("/guitarras")
@Produces(MediaType.APPLICATION_JSON)
public class GuitarraResource {

    @Inject
    GuitarraService service;

    @GET
    public Response listar() {
        return Response.ok(service.findAll().stream()
                .map(GuitarraResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(GuitarraResponseDTO.fromEntity(service.findById(id))).build();
    }

    // rotas no estilo original (/nome/{nome}, /marca/{marca}, ...), mantidas por compatibilidade
    @GET
    @Path("/nome/{nome}")
    public Response buscarPorNomeNoCaminho(@PathParam("nome") String nome) {
        return buscarPorNome(nome);
    }

    @GET
    @Path("/marca/{marca}")
    public Response buscarPorMarcaNoCaminho(@PathParam("marca") String marca) {
        return buscarPorMarca(marca);
    }

    @GET
    @Path("/modelo/{modelo}")
    public Response buscarPorModeloNoCaminho(@PathParam("modelo") String modelo) {
        return buscarPorModelo(modelo);
    }

    @GET
    @Path("/cor/{cor}")
    public Response buscarPorCorNoCaminho(@PathParam("cor") String cor) {
        return buscarPorCor(cor);
    }

    @GET
    @Path("/preco/{min}/{max}")
    public Response buscarPorPrecoNoCaminho(@PathParam("min") Double min, @PathParam("max") Double max) {
        return buscarPorPreco(min, max);
    }

    @GET
    @Path("/search")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        return Response.ok(service.findByNome(nome).stream()
                .map(GuitarraResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/search/marca")
    public Response buscarPorMarca(@QueryParam("nome") String marca) {
        return Response.ok(service.findByMarca(marca).stream()
                .map(GuitarraResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/search/modelo")
    public Response buscarPorModelo(@QueryParam("modelo") String modelo) {
        return Response.ok(service.findByModelo(modelo).stream()
                .map(GuitarraResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/search/cor")
    public Response buscarPorCor(@QueryParam("cor") String cor) {
        return Response.ok(service.findByCor(cor).stream()
                .map(GuitarraResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/search/preco")
    public Response buscarPorPreco(@QueryParam("min") Double min, @QueryParam("max") Double max) {
        return Response.ok(service.findByPreco(min, max).stream()
                .map(GuitarraResponseDTO::fromEntity)
                .toList()).build();
    }
}
