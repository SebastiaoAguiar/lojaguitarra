package br.unitins.lojaguitarra.dto;

import br.unitins.lojaguitarra.model.Captador;

public record CaptadorResponseDTO(
    Long id,
    String nome,
    String tipo) {

    public static CaptadorResponseDTO fromEntity(Captador captador) {
        return new CaptadorResponseDTO(
            captador.getId(),
            captador.getNome(),
            captador.getTipo());
    }
}
