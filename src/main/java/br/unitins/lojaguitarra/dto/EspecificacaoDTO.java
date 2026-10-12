package br.unitins.lojaguitarra.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EspecificacaoDTO(
    @NotBlank(message = "O formato do corpo deve ser informado.")
    @Size(min = 2, max = 40, message = "O formato do corpo deve ter entre 2 e 40 caracteres.")
    String formatoCorpo,

    @NotBlank(message = "A madeira do corpo deve ser informada.")
    @Size(min = 2, max = 40, message = "A madeira do corpo deve ter entre 2 e 40 caracteres.")
    String madeiraCorpo,

    @NotBlank(message = "A madeira da escala deve ser informada.")
    @Size(min = 2, max = 40, message = "A madeira da escala deve ter entre 2 e 40 caracteres.")
    String madeiraEscala,

    @NotNull(message = "O numero de trastes deve ser informado.")
    @Min(value = 18, message = "O numero de trastes deve ser no minimo 18.")
    @Max(value = 30, message = "O numero de trastes deve ser no maximo 30.")
    Integer numeroTrastes) {
}
