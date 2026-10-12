package br.unitins.lojaguitarra.resource;

import java.util.ArrayList;
import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraEletricaDTO;
import br.unitins.lojaguitarra.dto.GuitarraEletricaResponseDTO;
import br.unitins.lojaguitarra.model.Captador;
import br.unitins.lojaguitarra.model.Especificacao;
import br.unitins.lojaguitarra.model.GuitarraEletrica;
import br.unitins.lojaguitarra.model.TipoPonte;
import br.unitins.lojaguitarra.service.CaptadorService;
import br.unitins.lojaguitarra.service.GuitarraEletricaService;
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

@Path("/guitarras-eletricas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GuitarraEletricaResource {

    @Inject
    GuitarraEletricaService service;

    @Inject
    CaptadorService captadorService;

    @Inject
    MarcaService marcaService;

    @GET
    public Response listar() {
        return Response.ok(service.findAll().stream()
                .map(GuitarraEletricaResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(GuitarraEletricaResponseDTO.fromEntity(service.findById(id))).build();
    }

    @GET
    @Path("/search")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        return Response.ok(service.findByNome(nome).stream()
                .map(GuitarraEletricaResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/nome/{nome}")
    public Response buscarPorNomeNoCaminho(@PathParam("nome") String nome) {
        return buscarPorNome(nome);
    }

    @GET
    @Path("/ponte/{tipoPonte}")
    public Response buscarPorTipoPonte(@PathParam("tipoPonte") TipoPonte tipoPonte) {
        return Response.ok(service.findByTipoPonte(tipoPonte).stream()
                .map(GuitarraEletricaResponseDTO::fromEntity)
                .toList()).build();
    }

    @POST
    public Response inserir(@Valid GuitarraEletricaDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(GuitarraEletricaResponseDTO.fromEntity(service.create(toEntity(dto))))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid GuitarraEletricaDTO dto) {
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
    private GuitarraEletrica toEntity(GuitarraEletricaDTO dto) {
        GuitarraEletrica guitarra = new GuitarraEletrica();
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

        guitarra.setConfiguracaoCaptadores(dto.configuracaoCaptadores());
        guitarra.setCaptacaoAtiva(dto.captacaoAtiva());
        guitarra.setTipoPonte(dto.tipoPonte());

        List<Captador> captadores = new ArrayList<>();
        for (Long idCaptador : dto.idsCaptadores()) {
            captadores.add(captadorService.findById(idCaptador));
        }
        guitarra.setCaptadores(captadores);
        return guitarra;
    }
}
