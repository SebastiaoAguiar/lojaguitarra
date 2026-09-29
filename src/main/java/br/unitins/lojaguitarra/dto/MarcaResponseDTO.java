package br.unitins.lojaguitarra.dto;

import br.unitins.lojaguitarra.model.Marca;

public record MarcaResponseDTO(
    Long id,
    String nome
) {
    public static MarcaResponseDTO fromEntity(Marca marca) {
        return new MarcaResponseDTO(marca.getId(), marca.getNome());
    }
}
