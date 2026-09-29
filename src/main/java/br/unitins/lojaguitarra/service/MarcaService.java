package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.dto.MarcaDTO;
import br.unitins.lojaguitarra.dto.MarcaResponseDTO;

public interface MarcaService {
    MarcaResponseDTO create(MarcaDTO dto);
    void update(Long id, MarcaDTO dto);
    void delete(Long id);
    MarcaResponseDTO findById(Long id);
    List<MarcaResponseDTO> findByNome(String nome);
    List<MarcaResponseDTO> findAll();
}
