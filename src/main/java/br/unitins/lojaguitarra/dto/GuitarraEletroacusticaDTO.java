package br.unitins.lojaguitarra.dto;

import br.unitins.lojaguitarra.model.TipoCaptacao;
import br.unitins.lojaguitarra.model.TipoTampo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record GuitarraEletroacusticaDTO(
    @NotBlank(message = "O nome deve ser informado.")
    @Size(min = 2, max = 60, message = "O nome deve ter entre 2 e 60 caracteres.")
    String nome,

    @NotNull(message = "A marca deve ser informada.")
    @Positive(message = "Marca invalida.")
    Long idMarca,

    @NotBlank(message = "O modelo deve ser informado.")
    @Size(min = 2, max = 60, message = "O modelo deve ter entre 2 e 60 caracteres.")
    String modelo,

    @NotBlank(message = "A cor deve ser informada.")
    @Size(min = 3, max = 30, message = "A cor deve ter entre 3 e 30 caracteres.")
    String cor,

    @NotNull(message = "O preco deve ser informado.")
    @Positive(message = "O preco deve ser maior que zero.")
    Double preco,

    @NotNull(message = "O tipo de tampo deve ser informado.")
    TipoTampo tipoTampo,

    @NotNull(message = "Informe se a guitarra possui cutaway.")
    Boolean cutaway,

    @NotNull(message = "O tipo de captacao deve ser informado.")
    TipoCaptacao tipoCaptacao,

    @NotNull(message = "Informe se a guitarra possui afinador embutido.")
    Boolean afinadorEmbutido,

    @NotNull(message = "O numero de bandas do equalizador deve ser informado.")
    @Min(value = 0, message = "O numero de bandas nao pode ser negativo.")
    @Max(value = 10, message = "O numero de bandas deve ser no maximo 10.")
    Integer bandasEqualizador
) {
}
