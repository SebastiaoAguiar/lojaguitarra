package br.unitins.lojaguitarra.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CaptadorDTO(
    @NotBlank(message = "O nome deve ser informado.")
    @Size(min = 2, max = 60, message = "O nome deve ter entre 2 e 60 caracteres.")
    String nome,

    @NotBlank(message = "O tipo deve ser informado.")
    @Size(min = 3, max = 30, message = "O tipo deve ter entre 3 e 30 caracteres.")
    String tipo) {
}
