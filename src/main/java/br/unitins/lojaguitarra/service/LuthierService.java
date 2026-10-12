package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.model.Luthier;

public interface LuthierService {
    Luthier create(Luthier luthier);
    void update(Long id, Luthier luthier);
    void delete(Long id);
    Luthier findById(Long id);
    List<Luthier> findByNome(String nome);
    List<Luthier> findByEspecialidade(String especialidade);
    List<Luthier> findAll();
}
