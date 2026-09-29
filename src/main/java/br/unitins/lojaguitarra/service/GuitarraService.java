package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraDTO;
import br.unitins.lojaguitarra.dto.GuitarraResponseDTO;

public interface GuitarraService {
    GuitarraResponseDTO create(GuitarraDTO dto);
    void update(Long id, GuitarraDTO dto);
    void delete(Long id);
    GuitarraResponseDTO findById(Long id);
    List<GuitarraResponseDTO> findByNome(String nome);
    List<GuitarraResponseDTO> findByMarca(String marca);
    List<GuitarraResponseDTO> findByModelo(String modelo);
    List<GuitarraResponseDTO> findByCor(String cor);
    List<GuitarraResponseDTO> findByPreco(Double min, Double max);
    List<GuitarraResponseDTO> findAll();
}
