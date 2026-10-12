package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.model.GuitarraEletroacustica;
import br.unitins.lojaguitarra.model.TipoCaptacao;

public interface GuitarraEletroacusticaService {
    GuitarraEletroacustica create(GuitarraEletroacustica guitarra);
    void update(Long id, GuitarraEletroacustica guitarra);
    void delete(Long id);
    GuitarraEletroacustica findById(Long id);
    List<GuitarraEletroacustica> findByNome(String nome);
    List<GuitarraEletroacustica> findByTipoCaptacao(TipoCaptacao tipoCaptacao);
    List<GuitarraEletroacustica> findAll();
}
