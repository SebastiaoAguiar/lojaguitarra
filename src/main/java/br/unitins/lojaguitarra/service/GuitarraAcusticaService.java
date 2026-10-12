package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.model.GuitarraAcustica;
import br.unitins.lojaguitarra.model.TipoCorda;

public interface GuitarraAcusticaService {
    GuitarraAcustica create(GuitarraAcustica guitarra);
    void update(Long id, GuitarraAcustica guitarra);
    void delete(Long id);
    GuitarraAcustica findById(Long id);
    List<GuitarraAcustica> findByNome(String nome);
    List<GuitarraAcustica> findByTipoCorda(TipoCorda tipoCorda);
    List<GuitarraAcustica> findAll();
}
