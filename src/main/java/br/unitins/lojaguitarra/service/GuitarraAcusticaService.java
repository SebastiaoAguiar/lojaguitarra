package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraAcusticaDTO;
import br.unitins.lojaguitarra.dto.GuitarraAcusticaResponseDTO;
import br.unitins.lojaguitarra.model.TipoCorda;

public interface GuitarraAcusticaService {
    GuitarraAcusticaResponseDTO create(GuitarraAcusticaDTO dto);
    void update(Long id, GuitarraAcusticaDTO dto);
    void delete(Long id);
    GuitarraAcusticaResponseDTO findById(Long id);
    List<GuitarraAcusticaResponseDTO> findByNome(String nome);
    List<GuitarraAcusticaResponseDTO> findByTipoCorda(TipoCorda tipoCorda);
    List<GuitarraAcusticaResponseDTO> findAll();
}
