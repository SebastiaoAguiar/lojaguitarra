package br.unitins.lojaguitarra.dto;

import br.unitins.lojaguitarra.model.GuitarraEletroacustica;
import br.unitins.lojaguitarra.model.TipoCaptacao;
import br.unitins.lojaguitarra.model.TipoTampo;

public record GuitarraEletroacusticaResponseDTO(
    Long id,
    String nome,
    MarcaResponseDTO marca,
    String modelo,
    String cor,
    Double preco,
    TipoTampo tipoTampo,
    Boolean cutaway,
    TipoCaptacao tipoCaptacao,
    Boolean afinadorEmbutido,
    Integer bandasEqualizador
) {
    public static GuitarraEletroacusticaResponseDTO fromEntity(GuitarraEletroacustica guitarra) {
        return new GuitarraEletroacusticaResponseDTO(
            guitarra.getId(),
            guitarra.getNome(),
            MarcaResponseDTO.fromEntity(guitarra.getMarca()),
            guitarra.getModelo(),
            guitarra.getCor(),
            guitarra.getPreco(),
            guitarra.getTipoTampo(),
            guitarra.getCutaway(),
            guitarra.getTipoCaptacao(),
            guitarra.getAfinadorEmbutido(),
            guitarra.getBandasEqualizador()
        );
    }
}
