package br.unitins.lojaguitarra.resource;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraAcusticaDTO;
import br.unitins.lojaguitarra.dto.GuitarraAcusticaResponseDTO;
import br.unitins.lojaguitarra.model.TipoCorda;
import br.unitins.lojaguitarra.service.GuitarraAcusticaService;
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

@Path("/guitarras-acusticas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GuitarraAcusticaResource {

    @Inject
    GuitarraAcusticaService service;

    @GET
    public List<GuitarraAcusticaResponseDTO> listar() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public GuitarraAcusticaResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    @Path("/nome/{nome}")
    public List<GuitarraAcusticaResponseDTO> buscarPorNome(@PathParam("nome") String nome) {
        return service.findByNome(nome);
    }

    @GET
    @Path("/corda/{tipoCorda}")
    public List<GuitarraAcusticaResponseDTO> buscarPorTipoCorda(@PathParam("tipoCorda") TipoCorda tipoCorda) {
        return service.findByTipoCorda(tipoCorda);
    }

    @POST
    public GuitarraAcusticaResponseDTO inserir(@Valid GuitarraAcusticaDTO dto) {
        return service.create(dto);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, @Valid GuitarraAcusticaDTO dto) {
        service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void excluir(@PathParam("id") Long id) {
        service.delete(id);
    }
}
