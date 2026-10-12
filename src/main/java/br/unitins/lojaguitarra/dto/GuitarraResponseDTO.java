package br.unitins.lojaguitarra.dto;

import java.util.List;

import br.unitins.lojaguitarra.model.Guitarra;
import br.unitins.lojaguitarra.model.GuitarraAcustica;
import br.unitins.lojaguitarra.model.GuitarraEletrica;
import br.unitins.lojaguitarra.model.GuitarraEletroacustica;

// resposta da listagem polimorfica: dados comuns + o tipo da guitarra
// (captadores so existem nas guitarras eletricas; nas demais a lista vem vazia)
public record GuitarraResponseDTO(
    Long id,
    String tipo,
    String nome,
    MarcaResponseDTO marca,
    String modelo,
    String cor,
    Double preco,
    EspecificacaoResponseDTO especificacao,
    List<CaptadorResponseDTO> captadores
) {
    public static GuitarraResponseDTO fromEntity(Guitarra guitarra) {
        return new GuitarraResponseDTO(
            guitarra.getId(),
            tipo(guitarra),
            guitarra.getNome(),
            MarcaResponseDTO.fromEntity(guitarra.getMarca()),
            guitarra.getModelo(),
            guitarra.getCor(),
            guitarra.getPreco(),
            EspecificacaoResponseDTO.fromEntity(guitarra.getEspecificacao()),
            captadores(guitarra)
        );
    }

    private static List<CaptadorResponseDTO> captadores(Guitarra guitarra) {
        if (guitarra instanceof GuitarraEletrica eletrica) {
            return eletrica.getCaptadores().stream().map(CaptadorResponseDTO::fromEntity).toList();
        }
        return List.of();
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
