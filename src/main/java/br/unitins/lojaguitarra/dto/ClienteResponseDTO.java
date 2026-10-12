package br.unitins.lojaguitarra.dto;

import java.util.List;

import br.unitins.lojaguitarra.model.Cliente;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    String telefone,
    List<EnderecoResponseDTO> enderecos) {

    public static ClienteResponseDTO fromEntity(Cliente cliente) {
        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getCpf(),
            cliente.getEmail(),
            cliente.getTelefone(),
            cliente.getEnderecos().stream().map(EnderecoResponseDTO::fromEntity).toList());
    }
}
