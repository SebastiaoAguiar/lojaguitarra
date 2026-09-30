package br.unitins.lojaguitarra.resource;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraEletricaDTO;
import br.unitins.lojaguitarra.dto.GuitarraEletricaResponseDTO;
import br.unitins.lojaguitarra.model.TipoPonte;
import br.unitins.lojaguitarra.service.GuitarraEletricaService;
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

@Path("/guitarras-eletricas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GuitarraEletricaResource {

    @Inject
    GuitarraEletricaService service;

    @GET
    public List<GuitarraEletricaResponseDTO> listar() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public GuitarraEletricaResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    @Path("/nome/{nome}")
    public List<GuitarraEletricaResponseDTO> buscarPorNome(@PathParam("nome") String nome) {
        return service.findByNome(nome);
    }

    @GET
    @Path("/ponte/{tipoPonte}")
    public List<GuitarraEletricaResponseDTO> buscarPorTipoPonte(@PathParam("tipoPonte") TipoPonte tipoPonte) {
        return service.findByTipoPonte(tipoPonte);
    }

    @POST
    public GuitarraEletricaResponseDTO inserir(@Valid GuitarraEletricaDTO dto) {
        return service.create(dto);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, @Valid GuitarraEletricaDTO dto) {
        service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void excluir(@PathParam("id") Long id) {
        service.delete(id);
    }
}
