package br.unitins.lojaguitarra.resource;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraDTO;
import br.unitins.lojaguitarra.dto.GuitarraResponseDTO;
import br.unitins.lojaguitarra.service.GuitarraService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/guitarras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GuitarraResource {

    @Inject
    GuitarraService service;

    @GET
    public List<GuitarraResponseDTO> listar() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public GuitarraResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    @Path("/nome/{nome}")
    public List<GuitarraResponseDTO> buscarPorNome(@PathParam("nome") String nome) {
        return service.findByNome(nome);
    }

    @GET
    @Path("/marca/{marca}")
    public List<GuitarraResponseDTO> buscarPorMarca(@PathParam("marca") String marca) {
        return service.findByMarca(marca);
    }

    @GET
    @Path("/modelo/{modelo}")
    public List<GuitarraResponseDTO> buscarPorModelo(@PathParam("modelo") String modelo) {
        return service.findByModelo(modelo);
    }

    @GET
    @Path("/cor/{cor}")
    public List<GuitarraResponseDTO> buscarPorCor(@PathParam("cor") String cor) {
        return service.findByCor(cor);
    }

    @GET
    @Path("/preco/{min}/{max}")
    public List<GuitarraResponseDTO> buscarPorPreco(@PathParam("min") Double min, @PathParam("max") Double max) {
        return service.findByPreco(min, max);
    }

    @POST
    public GuitarraResponseDTO inserir(@Valid GuitarraDTO dto) {
        return service.create(dto);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, @Valid GuitarraDTO dto) {
        service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void excluir(@PathParam("id") Long id) {
        service.delete(id);
    }
}
