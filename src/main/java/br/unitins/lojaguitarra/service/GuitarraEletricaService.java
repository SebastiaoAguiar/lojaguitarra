package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraEletricaDTO;
import br.unitins.lojaguitarra.dto.GuitarraEletricaResponseDTO;
import br.unitins.lojaguitarra.model.TipoPonte;

public interface GuitarraEletricaService {
    GuitarraEletricaResponseDTO create(GuitarraEletricaDTO dto);
    void update(Long id, GuitarraEletricaDTO dto);
    void delete(Long id);
    GuitarraEletricaResponseDTO findById(Long id);
    List<GuitarraEletricaResponseDTO> findByNome(String nome);
    List<GuitarraEletricaResponseDTO> findByTipoPonte(TipoPonte tipoPonte);
    List<GuitarraEletricaResponseDTO> findAll();
}
