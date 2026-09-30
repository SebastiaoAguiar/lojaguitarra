package br.unitins.lojaguitarra.dto;

import br.unitins.lojaguitarra.model.Guitarra;
import br.unitins.lojaguitarra.model.GuitarraAcustica;
import br.unitins.lojaguitarra.model.GuitarraEletrica;
import br.unitins.lojaguitarra.model.GuitarraEletroacustica;

// resposta da listagem polimorfica: so os dados comuns + o tipo da guitarra
public record GuitarraResponseDTO(
    Long id,
    String tipo,
    String nome,
    MarcaResponseDTO marca,
    String modelo,
    String cor,
    Double preco
) {
    public static GuitarraResponseDTO fromEntity(Guitarra guitarra) {
        return new GuitarraResponseDTO(
            guitarra.getId(),
            tipo(guitarra),
            guitarra.getNome(),
            MarcaResponseDTO.fromEntity(guitarra.getMarca()),
            guitarra.getModelo(),
            guitarra.getCor(),
            guitarra.getPreco()
        );
    }

    private static String tipo(Guitarra guitarra) {
        return switch (guitarra) {
            case GuitarraEletrica _ -> "ELETRICA";
            case GuitarraAcustica _ -> "ACUSTICA";
            case GuitarraEletroacustica _ -> "ELETROACUSTICA";
            default -> "DESCONHECIDO";
        };
    }
}
