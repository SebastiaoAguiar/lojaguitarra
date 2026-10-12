package br.unitins.lojaguitarra.resource;

import br.unitins.lojaguitarra.dto.GuitarraAcusticaDTO;
import br.unitins.lojaguitarra.dto.GuitarraAcusticaResponseDTO;
import br.unitins.lojaguitarra.model.Especificacao;
import br.unitins.lojaguitarra.model.GuitarraAcustica;
import br.unitins.lojaguitarra.model.TipoCorda;
import br.unitins.lojaguitarra.service.GuitarraAcusticaService;
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

@Path("/guitarras-acusticas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GuitarraAcusticaResource {

    @Inject
    GuitarraAcusticaService service;

    @Inject
    MarcaService marcaService;

    @GET
    public Response listar() {
        return Response.ok(service.findAll().stream()
                .map(GuitarraAcusticaResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(GuitarraAcusticaResponseDTO.fromEntity(service.findById(id))).build();
    }

    @GET
    @Path("/search")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        return Response.ok(service.findByNome(nome).stream()
                .map(GuitarraAcusticaResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/nome/{nome}")
    public Response buscarPorNomeNoCaminho(@PathParam("nome") String nome) {
        return buscarPorNome(nome);
    }

    @GET
    @Path("/corda/{tipoCorda}")
    public Response buscarPorTipoCorda(@PathParam("tipoCorda") TipoCorda tipoCorda) {
        return Response.ok(service.findByTipoCorda(tipoCorda).stream()
                .map(GuitarraAcusticaResponseDTO::fromEntity)
                .toList()).build();
    }

    @POST
    public Response inserir(@Valid GuitarraAcusticaDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(GuitarraAcusticaResponseDTO.fromEntity(service.create(toEntity(dto))))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid GuitarraAcusticaDTO dto) {
        service.update(id, toEntity(dto));
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    private GuitarraAcustica toEntity(GuitarraAcusticaDTO dto) {
        GuitarraAcustica guitarra = new GuitarraAcustica();
        guitarra.setNome(dto.nome());
        guitarra.setMarca(marcaService.findById(dto.idMarca()));
        guitarra.setModelo(dto.modelo());
        guitarra.setCor(dto.cor());
        guitarra.setPreco(dto.preco());

        Especificacao especificacao = new Especificacao();
        especificacao.setFormatoCorpo(dto.especificacao().formatoCorpo());
        especificacao.setMadeiraCorpo(dto.especificacao().madeiraCorpo());
        especificacao.setMadeiraEscala(dto.especificacao().madeiraEscala());
        especificacao.setNumeroTrastes(dto.especificacao().numeroTrastes());
        guitarra.setEspecificacao(especificacao);

        guitarra.setTipoTampo(dto.tipoTampo());
        guitarra.setTipoCorda(dto.tipoCorda());
        guitarra.setCutaway(dto.cutaway());
        return guitarra;
    }
}
