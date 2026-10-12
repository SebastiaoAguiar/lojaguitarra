package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.model.Municipio;

public interface MunicipioService {
    Municipio create(Municipio municipio);
    void update(Long id, Municipio municipio);
    void delete(Long id);
    Municipio findById(Long id);
    List<Municipio> findByNome(String nome);
    List<Municipio> findAll();
}
