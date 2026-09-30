package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraResponseDTO;

public interface GuitarraService {
    GuitarraResponseDTO findById(Long id);
    List<GuitarraResponseDTO> findByNome(String nome);
    List<GuitarraResponseDTO> findByMarca(String marca);
    List<GuitarraResponseDTO> findByModelo(String modelo);
    List<GuitarraResponseDTO> findByCor(String cor);
    List<GuitarraResponseDTO> findByPreco(Double min, Double max);
    List<GuitarraResponseDTO> findAll();
}
