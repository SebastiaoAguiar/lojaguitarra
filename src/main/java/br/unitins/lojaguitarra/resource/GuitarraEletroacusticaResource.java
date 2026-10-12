package br.unitins.lojaguitarra.resource;

import br.unitins.lojaguitarra.dto.GuitarraEletroacusticaDTO;
import br.unitins.lojaguitarra.dto.GuitarraEletroacusticaResponseDTO;
import br.unitins.lojaguitarra.model.Especificacao;
import br.unitins.lojaguitarra.model.GuitarraEletroacustica;
import br.unitins.lojaguitarra.model.TipoCaptacao;
import br.unitins.lojaguitarra.service.GuitarraEletroacusticaService;
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

@Path("/guitarras-eletroacusticas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GuitarraEletroacusticaResource {

    @Inject
    GuitarraEletroacusticaService service;

    @Inject
    MarcaService marcaService;

    @GET
    public Response listar() {
        return Response.ok(service.findAll().stream()
                .map(GuitarraEletroacusticaResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(GuitarraEletroacusticaResponseDTO.fromEntity(service.findById(id))).build();
    }

    @GET
    @Path("/search")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        return Response.ok(service.findByNome(nome).stream()
                .map(GuitarraEletroacusticaResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/nome/{nome}")
    public Response buscarPorNomeNoCaminho(@PathParam("nome") String nome) {
        return buscarPorNome(nome);
    }

    @GET
    @Path("/captacao/{tipoCaptacao}")
    public Response buscarPorTipoCaptacao(@PathParam("tipoCaptacao") TipoCaptacao tipoCaptacao) {
        return Response.ok(service.findByTipoCaptacao(tipoCaptacao).stream()
                .map(GuitarraEletroacusticaResponseDTO::fromEntity)
                .toList()).build();
    }

    @POST
    public Response inserir(@Valid GuitarraEletroacusticaDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(GuitarraEletroacusticaResponseDTO.fromEntity(service.create(toEntity(dto))))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid GuitarraEletroacusticaDTO dto) {
        service.update(id, toEntity(dto));
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    private GuitarraEletroacustica toEntity(GuitarraEletroacusticaDTO dto) {
        GuitarraEletroacustica guitarra = new GuitarraEletroacustica();
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
        guitarra.setCutaway(dto.cutaway());
        guitarra.setTipoCaptacao(dto.tipoCaptacao());
        guitarra.setAfinadorEmbutido(dto.afinadorEmbutido());
        guitarra.setBandasEqualizador(dto.bandasEqualizador());
        return guitarra;
    }
}
