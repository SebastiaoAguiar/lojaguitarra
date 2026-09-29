package br.unitins.lojaguitarra.resource;

import java.util.List;

import br.unitins.lojaguitarra.dto.MarcaDTO;
import br.unitins.lojaguitarra.dto.MarcaResponseDTO;
import br.unitins.lojaguitarra.service.MarcaService;
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

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {

    @Inject
    MarcaService service;

    @GET
    public List<MarcaResponseDTO> listar() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public MarcaResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    @Path("/nome/{nome}")
    public List<MarcaResponseDTO> buscarPorNome(@PathParam("nome") String nome) {
        return service.findByNome(nome);
    }

    @POST
    public MarcaResponseDTO inserir(@Valid MarcaDTO dto) {
        return service.create(dto);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, @Valid MarcaDTO dto) {
        service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void excluir(@PathParam("id") Long id) {
        service.delete(id);
    }
}
