package br.unitins.lojaguitarra.dto;

import java.util.List;

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
    EspecificacaoResponseDTO especificacao,
    ConfiguracaoCaptadores configuracaoCaptadores,
    Boolean captacaoAtiva,
    TipoPonte tipoPonte,
    List<CaptadorResponseDTO> captadores
) {
    public static GuitarraEletricaResponseDTO fromEntity(GuitarraEletrica guitarra) {
        return new GuitarraEletricaResponseDTO(
            guitarra.getId(),
            guitarra.getNome(),
            MarcaResponseDTO.fromEntity(guitarra.getMarca()),
            guitarra.getModelo(),
            guitarra.getCor(),
            guitarra.getPreco(),
            EspecificacaoResponseDTO.fromEntity(guitarra.getEspecificacao()),
            guitarra.getConfiguracaoCaptadores(),
            guitarra.getCaptacaoAtiva(),
            guitarra.getTipoPonte(),
            guitarra.getCaptadores().stream().map(CaptadorResponseDTO::fromEntity).toList()
        );
    }
}
