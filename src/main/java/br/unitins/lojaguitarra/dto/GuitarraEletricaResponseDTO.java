package br.unitins.lojaguitarra.dto;

import br.unitins.lojaguitarra.model.ConfiguracaoCaptadores;
import br.unitins.lojaguitarra.model.GuitarraEletrica;
import br.unitins.lojaguitarra.model.TipoPonte;

public record GuitarraEletricaResponseDTO(
    Long id,
    String nome,
    MarcaResponseDTO marca,
    String modelo,
    String cor,
    Double preco,
    ConfiguracaoCaptadores configuracaoCaptadores,
    Boolean captacaoAtiva,
    TipoPonte tipoPonte
) {
    public static GuitarraEletricaResponseDTO fromEntity(GuitarraEletrica guitarra) {
        return new GuitarraEletricaResponseDTO(
            guitarra.getId(),
            guitarra.getNome(),
            MarcaResponseDTO.fromEntity(guitarra.getMarca()),
            guitarra.getModelo(),
            guitarra.getCor(),
            guitarra.getPreco(),
            guitarra.getConfiguracaoCaptadores(),
            guitarra.getCaptacaoAtiva(),
            guitarra.getTipoPonte()
        );
    }
}
