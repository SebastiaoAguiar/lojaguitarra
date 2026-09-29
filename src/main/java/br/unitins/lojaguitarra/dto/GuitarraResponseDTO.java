package br.unitins.lojaguitarra.dto;

import br.unitins.lojaguitarra.model.Guitarra;

public record GuitarraResponseDTO(
    Long id,
    String nome,
    MarcaResponseDTO marca,
    String modelo,
    String cor,
    Double preco
) {
    public static GuitarraResponseDTO fromEntity(Guitarra guitarra) {
        return new GuitarraResponseDTO(
            guitarra.getId(),
            guitarra.getNome(),
            MarcaResponseDTO.fromEntity(guitarra.getMarca()),
            guitarra.getModelo(),
            guitarra.getCor(),
            guitarra.getPreco()
        );
    }
}
