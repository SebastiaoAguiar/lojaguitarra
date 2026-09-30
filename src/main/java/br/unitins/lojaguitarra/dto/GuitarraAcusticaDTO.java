package br.unitins.lojaguitarra.dto;

import br.unitins.lojaguitarra.model.TipoCorda;
import br.unitins.lojaguitarra.model.TipoTampo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record GuitarraAcusticaDTO(
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

    @NotNull(message = "O tipo de corda deve ser informado.")
    TipoCorda tipoCorda,

    @NotNull(message = "Informe se a guitarra possui cutaway.")
    Boolean cutaway
) {
}
