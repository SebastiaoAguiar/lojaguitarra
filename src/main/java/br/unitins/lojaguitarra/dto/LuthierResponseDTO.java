package br.unitins.lojaguitarra.dto;

import br.unitins.lojaguitarra.model.Luthier;

public record LuthierResponseDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    String especialidade,
    Integer anosExperiencia) {

    public static LuthierResponseDTO fromEntity(Luthier luthier) {
        return new LuthierResponseDTO(
            luthier.getId(),
            luthier.getNome(),
            luthier.getCpf(),
            luthier.getEmail(),
            luthier.getEspecialidade(),
            luthier.getAnosExperiencia());
    }
}
