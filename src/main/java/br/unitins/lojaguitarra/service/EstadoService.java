package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.model.Estado;

public interface EstadoService {
    Estado create(Estado estado);
    void update(Long id, Estado estado);
    void delete(Long id);
    Estado findById(Long id);
    List<Estado> findByNome(String nome);
    List<Estado> findAll();
}
