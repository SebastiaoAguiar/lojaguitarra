package br.unitins.lojaguitarra.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EnderecoDTO(
    @NotBlank(message = "O logradouro deve ser informado.")
    @Size(min = 3, max = 100, message = "O logradouro deve ter entre 3 e 100 caracteres.")
    String logradouro,

    @NotBlank(message = "O numero deve ser informado.")
    @Size(max = 10, message = "O numero deve ter no maximo 10 caracteres.")
    String numero,

    @Size(max = 60, message = "O complemento deve ter no maximo 60 caracteres.")
    String complemento,

    @NotBlank(message = "O bairro deve ser informado.")
    @Size(min = 2, max = 60, message = "O bairro deve ter entre 2 e 60 caracteres.")
    String bairro,

    @NotBlank(message = "O CEP deve ser informado.")
    @Pattern(regexp = "^\\d{8}$", message = "O CEP deve conter 8 digitos.")
    String cep,

    @NotNull(message = "O municipio deve ser informado.")
    @Positive(message = "Municipio invalido.")
    Long idMunicipio) {
}
