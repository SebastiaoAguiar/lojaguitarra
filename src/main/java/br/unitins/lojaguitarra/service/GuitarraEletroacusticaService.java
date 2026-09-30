package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraEletroacusticaDTO;
import br.unitins.lojaguitarra.dto.GuitarraEletroacusticaResponseDTO;
import br.unitins.lojaguitarra.model.TipoCaptacao;

public interface GuitarraEletroacusticaService {
    GuitarraEletroacusticaResponseDTO create(GuitarraEletroacusticaDTO dto);
    void update(Long id, GuitarraEletroacusticaDTO dto);
    void delete(Long id);
    GuitarraEletroacusticaResponseDTO findById(Long id);
    List<GuitarraEletroacusticaResponseDTO> findByNome(String nome);
    List<GuitarraEletroacusticaResponseDTO> findByTipoCaptacao(TipoCaptacao tipoCaptacao);
    List<GuitarraEletroacusticaResponseDTO> findAll();
}
