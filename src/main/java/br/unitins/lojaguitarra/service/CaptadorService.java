package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.model.Captador;

public interface CaptadorService {
    Captador create(Captador captador);
    void update(Long id, Captador captador);
    void delete(Long id);
    Captador findById(Long id);
    List<Captador> findByNome(String nome);
    List<Captador> findAll();
}
