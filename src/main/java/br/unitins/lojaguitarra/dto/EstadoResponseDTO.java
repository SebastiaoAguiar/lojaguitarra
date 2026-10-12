package br.unitins.lojaguitarra.dto;

import br.unitins.lojaguitarra.model.Estado;
import br.unitins.lojaguitarra.model.Regiao;

public record EstadoResponseDTO(
    Long id,
    String nome,
    String sigla,
    Regiao regiao) {

    public static EstadoResponseDTO fromEntity(Estado estado) {
        return new EstadoResponseDTO(
            estado.getId(),
            estado.getNome(),
            estado.getSigla(),
            estado.getRegiao());
    }
}
