package br.unitins.lojaguitarra.resource;

import java.util.ArrayList;
import java.util.List;

import br.unitins.lojaguitarra.dto.ClienteDTO;
import br.unitins.lojaguitarra.dto.ClienteResponseDTO;
import br.unitins.lojaguitarra.dto.EnderecoDTO;
import br.unitins.lojaguitarra.model.Cliente;
import br.unitins.lojaguitarra.model.Endereco;
import br.unitins.lojaguitarra.service.ClienteService;
import br.unitins.lojaguitarra.service.MunicipioService;
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

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService service;

    @Inject
    MunicipioService municipioService;

    @GET
    public Response listar() {
        return Response.ok(service.findAll().stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(ClienteResponseDTO.fromEntity(service.findById(id))).build();
    }

    @GET
    @Path("/search")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        return Response.ok(service.findByNome(nome).stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList()).build();
    }

    @POST
    public Response inserir(@Valid ClienteDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(ClienteResponseDTO.fromEntity(service.create(toEntity(dto))))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid ClienteDTO dto) {
        service.update(id, toEntity(dto));
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    // converte o DTO em entidade, buscando o municipio de cada endereco pelo id
    private Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());

        List<Endereco> enderecos = new ArrayList<>();
        for (EnderecoDTO enderecoDto : dto.enderecos()) {
            Endereco endereco = new Endereco();
            endereco.setLogradouro(enderecoDto.logradouro());
            endereco.setNumero(enderecoDto.numero());
            endereco.setComplemento(enderecoDto.complemento());
            endereco.setBairro(enderecoDto.bairro());
            endereco.setCep(enderecoDto.cep());
            endereco.setMunicipio(municipioService.findById(enderecoDto.idMunicipio()));
            enderecos.add(endereco);
        }
        cliente.setEnderecos(enderecos);
        return cliente;
    }
}
