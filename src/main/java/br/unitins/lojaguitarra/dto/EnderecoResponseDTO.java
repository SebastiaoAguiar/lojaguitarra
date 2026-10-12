package br.unitins.lojaguitarra.dto;

import br.unitins.lojaguitarra.model.Endereco;

public record EnderecoResponseDTO(
    Long id,
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cep,
    MunicipioResponseDTO municipio) {

    public static EnderecoResponseDTO fromEntity(Endereco endereco) {
        return new EnderecoResponseDTO(
            endereco.getId(),
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getComplemento(),
            endereco.getBairro(),
            endereco.getCep(),
            MunicipioResponseDTO.fromEntity(endereco.getMunicipio()));
    }
}
