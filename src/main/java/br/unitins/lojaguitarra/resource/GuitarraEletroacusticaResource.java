package br.unitins.lojaguitarra.resource;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraEletroacusticaDTO;
import br.unitins.lojaguitarra.dto.GuitarraEletroacusticaResponseDTO;
import br.unitins.lojaguitarra.model.TipoCaptacao;
import br.unitins.lojaguitarra.service.GuitarraEletroacusticaService;
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

@Path("/guitarras-eletroacusticas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GuitarraEletroacusticaResource {

    @Inject
    GuitarraEletroacusticaService service;

    @GET
    public List<GuitarraEletroacusticaResponseDTO> listar() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public GuitarraEletroacusticaResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    @Path("/nome/{nome}")
    public List<GuitarraEletroacusticaResponseDTO> buscarPorNome(@PathParam("nome") String nome) {
        return service.findByNome(nome);
    }

    @GET
    @Path("/captacao/{tipoCaptacao}")
    public List<GuitarraEletroacusticaResponseDTO> buscarPorTipoCaptacao(@PathParam("tipoCaptacao") TipoCaptacao tipoCaptacao) {
        return service.findByTipoCaptacao(tipoCaptacao);
    }

    @POST
    public GuitarraEletroacusticaResponseDTO inserir(@Valid GuitarraEletroacusticaDTO dto) {
        return service.create(dto);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, @Valid GuitarraEletroacusticaDTO dto) {
        service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void excluir(@PathParam("id") Long id) {
        service.delete(id);
    }
}
