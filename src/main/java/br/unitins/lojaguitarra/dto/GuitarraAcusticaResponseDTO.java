package br.unitins.lojaguitarra.dto;

import br.unitins.lojaguitarra.model.GuitarraAcustica;
import br.unitins.lojaguitarra.model.TipoCorda;
import br.unitins.lojaguitarra.model.TipoTampo;

public record GuitarraAcusticaResponseDTO(
    Long id,
    String nome,
    MarcaResponseDTO marca,
    String modelo,
    String cor,
    Double preco,
    TipoTampo tipoTampo,
    TipoCorda tipoCorda,
    Boolean cutaway
) {
    public static GuitarraAcusticaResponseDTO fromEntity(GuitarraAcustica guitarra) {
        return new GuitarraAcusticaResponseDTO(
            guitarra.getId(),
            guitarra.getNome(),
            MarcaResponseDTO.fromEntity(guitarra.getMarca()),
            guitarra.getModelo(),
            guitarra.getCor(),
            guitarra.getPreco(),
            guitarra.getTipoTampo(),
            guitarra.getTipoCorda(),
            guitarra.getCutaway()
        );
    }
}
