package br.unitins.lojaguitarra.dto;

import br.unitins.lojaguitarra.model.Especificacao;

public record EspecificacaoResponseDTO(
    Long id,
    String formatoCorpo,
    String madeiraCorpo,
    String madeiraEscala,
    Integer numeroTrastes) {

    public static EspecificacaoResponseDTO fromEntity(Especificacao especificacao) {
        return new EspecificacaoResponseDTO(
            especificacao.getId(),
            especificacao.getFormatoCorpo(),
            especificacao.getMadeiraCorpo(),
            especificacao.getMadeiraEscala(),
            especificacao.getNumeroTrastes());
    }
}
