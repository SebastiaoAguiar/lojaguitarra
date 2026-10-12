package br.unitins.lojaguitarra.resource;

import java.util.ArrayList;
import java.util.List;

import br.unitins.lojaguitarra.dto.EspecificacaoDTO;
import br.unitins.lojaguitarra.dto.GuitarraDTO;
import br.unitins.lojaguitarra.dto.GuitarraResponseDTO;
import br.unitins.lojaguitarra.model.Captador;
import br.unitins.lojaguitarra.model.Especificacao;
import br.unitins.lojaguitarra.model.Guitarra;
import br.unitins.lojaguitarra.service.CaptadorService;
import br.unitins.lojaguitarra.service.GuitarraService;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/guitarras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GuitarraResource {

    @Inject
    GuitarraService service;

    @Inject
    MarcaService marcaService;

    @Inject
    CaptadorService captadorService;

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

    @POST
    public Response inserir(@Valid GuitarraDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(GuitarraResponseDTO.fromEntity(service.create(toEntity(dto))))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid GuitarraDTO dto) {
        service.update(id, toEntity(dto));
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    // converte o DTO em entidade, buscando marca e captadores pelo id
    private Guitarra toEntity(GuitarraDTO dto) {
        Guitarra guitarra = new Guitarra();
        guitarra.setNome(dto.nome());
        guitarra.setMarca(marcaService.findById(dto.idMarca()));
        guitarra.setModelo(dto.modelo());
        guitarra.setCor(dto.cor());
        guitarra.setPreco(dto.preco());
        guitarra.setEspecificacao(toEspecificacao(dto.especificacao()));

        List<Captador> captadores = new ArrayList<>();
        for (Long idCaptador : dto.idsCaptadores()) {
            captadores.add(captadorService.findById(idCaptador));
        }
        guitarra.setCaptadores(captadores);
        return guitarra;
    }

    private Especificacao toEspecificacao(EspecificacaoDTO dto) {
        Especificacao especificacao = new Especificacao();
        especificacao.setFormatoCorpo(dto.formatoCorpo());
        especificacao.setMadeiraCorpo(dto.madeiraCorpo());
        especificacao.setMadeiraEscala(dto.madeiraEscala());
        especificacao.setNumeroTrastes(dto.numeroTrastes());
        return especificacao;
    }
}
