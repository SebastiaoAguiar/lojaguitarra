package br.unitins.lojaguitarra.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LuthierDTO(
    @NotBlank(message = "O nome deve ser informado.")
    @Size(min = 2, max = 60, message = "O nome deve ter entre 2 e 60 caracteres.")
    String nome,

    @NotBlank(message = "O CPF deve ser informado.")
    @Pattern(regexp = "^\\d{11}$", message = "O CPF deve conter 11 digitos.")
    String cpf,

    @NotBlank(message = "O email deve ser informado.")
    @Size(min = 5, max = 100, message = "O email deve ter entre 5 e 100 caracteres.")
    String email,

    @NotBlank(message = "A especialidade deve ser informada.")
    @Size(min = 3, max = 60, message = "A especialidade deve ter entre 3 e 60 caracteres.")
    String especialidade,

    @NotNull(message = "Os anos de experiencia devem ser informados.")
    @Min(value = 0, message = "Os anos de experiencia nao podem ser negativos.")
    @Max(value = 60, message = "Os anos de experiencia devem ser no maximo 60.")
    Integer anosExperiencia) {
}
