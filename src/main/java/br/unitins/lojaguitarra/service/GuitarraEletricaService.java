package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.model.GuitarraEletrica;
import br.unitins.lojaguitarra.model.TipoPonte;

public interface GuitarraEletricaService {
    GuitarraEletrica create(GuitarraEletrica guitarra);
    void update(Long id, GuitarraEletrica guitarra);
    void delete(Long id);
    GuitarraEletrica findById(Long id);
    List<GuitarraEletrica> findByNome(String nome);
    List<GuitarraEletrica> findByTipoPonte(TipoPonte tipoPonte);
    List<GuitarraEletrica> findAll();
}
