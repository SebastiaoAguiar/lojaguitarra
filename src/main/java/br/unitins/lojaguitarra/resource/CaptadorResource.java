package br.unitins.lojaguitarra.resource;

import br.unitins.lojaguitarra.dto.CaptadorDTO;
import br.unitins.lojaguitarra.dto.CaptadorResponseDTO;
import br.unitins.lojaguitarra.model.Captador;
import br.unitins.lojaguitarra.service.CaptadorService;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/captadores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CaptadorResource {

    @Inject
    CaptadorService service;

    @GET
    public Response listar() {
        return Response.ok(service.findAll().stream()
                .map(CaptadorResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(CaptadorResponseDTO.fromEntity(service.findById(id))).build();
    }

    @GET
    @Path("/search")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        return Response.ok(service.findByNome(nome).stream()
                .map(CaptadorResponseDTO::fromEntity)
                .toList()).build();
    }

    @POST
    public Response inserir(@Valid CaptadorDTO dto) {
        Captador captador = new Captador();
        captador.setNome(dto.nome());
        captador.setTipo(dto.tipo());
        return Response.status(Response.Status.CREATED)
                .entity(CaptadorResponseDTO.fromEntity(service.create(captador)))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid CaptadorDTO dto) {
        Captador captador = new Captador();
        captador.setNome(dto.nome());
        captador.setTipo(dto.tipo());
        service.update(id, captador);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
