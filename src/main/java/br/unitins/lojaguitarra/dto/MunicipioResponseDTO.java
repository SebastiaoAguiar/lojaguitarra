package br.unitins.lojaguitarra.dto;

import br.unitins.lojaguitarra.model.Municipio;

public record MunicipioResponseDTO(
    Long id,
    String nome,
    EstadoResponseDTO estado) {

    public static MunicipioResponseDTO fromEntity(Municipio municipio) {
        return new MunicipioResponseDTO(
            municipio.getId(),
            municipio.getNome(),
            EstadoResponseDTO.fromEntity(municipio.getEstado()));
    }
}
