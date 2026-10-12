package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.model.Guitarra;

public interface GuitarraService {
    Guitarra create(Guitarra guitarra);
    void update(Long id, Guitarra guitarra);
    void delete(Long id);
    Guitarra findById(Long id);
    List<Guitarra> findByNome(String nome);
    List<Guitarra> findByMarca(String marca);
    List<Guitarra> findByModelo(String modelo);
    List<Guitarra> findByCor(String cor);
    List<Guitarra> findByPreco(Double min, Double max);
    List<Guitarra> findAll();
}
