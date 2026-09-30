package br.unitins.lojaguitarra.resource;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraResponseDTO;
import br.unitins.lojaguitarra.service.GuitarraService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

// listagem polimorfica: traz eletricas, acusticas e eletroacusticas juntas
// o cadastro e feito nos resources de cada tipo
@Path("/guitarras")
@Produces(MediaType.APPLICATION_JSON)
public class GuitarraResource {

    @Inject
    GuitarraService service;

    @GET
    public List<GuitarraResponseDTO> listar() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public GuitarraResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    @Path("/nome/{nome}")
    public List<GuitarraResponseDTO> buscarPorNome(@PathParam("nome") String nome) {
        return service.findByNome(nome);
    }

    @GET
    @Path("/marca/{marca}")
    public List<GuitarraResponseDTO> buscarPorMarca(@PathParam("marca") String marca) {
        return service.findByMarca(marca);
    }

    @GET
    @Path("/modelo/{modelo}")
    public List<GuitarraResponseDTO> buscarPorModelo(@PathParam("modelo") String modelo) {
        return service.findByModelo(modelo);
    }

    @GET
    @Path("/cor/{cor}")
    public List<GuitarraResponseDTO> buscarPorCor(@PathParam("cor") String cor) {
        return service.findByCor(cor);
    }

    @GET
    @Path("/preco/{min}/{max}")
    public List<GuitarraResponseDTO> buscarPorPreco(@PathParam("min") Double min, @PathParam("max") Double max) {
        return service.findByPreco(min, max);
    }
}
