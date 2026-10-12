package br.unitins.lojaguitarra.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteDTO(
    @NotBlank(message = "O nome deve ser informado.")
    @Size(min = 2, max = 60, message = "O nome deve ter entre 2 e 60 caracteres.")
    String nome,

    @NotBlank(message = "O CPF deve ser informado.")
    @Pattern(regexp = "^\\d{11}$", message = "O CPF deve conter 11 digitos.")
    String cpf,

    @NotBlank(message = "O email deve ser informado.")
    @Size(min = 5, max = 100, message = "O email deve ter entre 5 e 100 caracteres.")
    String email,

    @NotBlank(message = "O telefone deve ser informado.")
    @Size(min = 8, max = 20, message = "O telefone deve ter entre 8 e 20 caracteres.")
    String telefone,

    @NotEmpty(message = "Informe ao menos um endereco.")
    List<@Valid EnderecoDTO> enderecos) {
}
