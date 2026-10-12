package br.unitins.lojaguitarra.resource;

import br.unitins.lojaguitarra.dto.LuthierDTO;
import br.unitins.lojaguitarra.dto.LuthierResponseDTO;
import br.unitins.lojaguitarra.model.Luthier;
import br.unitins.lojaguitarra.service.LuthierService;
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

@Path("/luthiers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LuthierResource {

    @Inject
    LuthierService service;

    @GET
    public Response listar() {
        return Response.ok(service.findAll().stream()
                .map(LuthierResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(LuthierResponseDTO.fromEntity(service.findById(id))).build();
    }

    @GET
    @Path("/search")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        return Response.ok(service.findByNome(nome).stream()
                .map(LuthierResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/search/especialidade")
    public Response buscarPorEspecialidade(@QueryParam("especialidade") String especialidade) {
        return Response.ok(service.findByEspecialidade(especialidade).stream()
                .map(LuthierResponseDTO::fromEntity)
                .toList()).build();
    }

    @POST
    public Response inserir(@Valid LuthierDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(LuthierResponseDTO.fromEntity(service.create(toEntity(dto))))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid LuthierDTO dto) {
        service.update(id, toEntity(dto));
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    private Luthier toEntity(LuthierDTO dto) {
        Luthier luthier = new Luthier();
        luthier.setNome(dto.nome());
        luthier.setCpf(dto.cpf());
        luthier.setEmail(dto.email());
        luthier.setEspecialidade(dto.especialidade());
        luthier.setAnosExperiencia(dto.anosExperiencia());
        return luthier;
    }
}
