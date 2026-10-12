package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.model.Guitarra;

// consulta polimorfica: devolve eletricas, acusticas e eletroacusticas juntas
// (o cadastro, a edicao e a exclusao ficam nos services de cada tipo)
public interface GuitarraService {
    Guitarra findById(Long id);
    List<Guitarra> findByNome(String nome);
    List<Guitarra> findByMarca(String marca);
    List<Guitarra> findByModelo(String modelo);
    List<Guitarra> findByCor(String cor);
    List<Guitarra> findByPreco(Double min, Double max);
    List<Guitarra> findAll();
}
