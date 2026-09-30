package br.unitins.lojaguitarra.repository;

import java.util.List;

import br.unitins.lojaguitarra.model.GuitarraEletroacustica;
import br.unitins.lojaguitarra.model.TipoCaptacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GuitarraEletroacusticaRepository implements PanacheRepository<GuitarraEletroacustica> {
    public List<GuitarraEletroacustica> findByNome(String nome) {
        return find("upper(nome) LIKE upper(?1)", "%" + nome + "%").list();
    }

    public List<GuitarraEletroacustica> findByTipoCaptacao(TipoCaptacao tipoCaptacao) {
        return find("tipoCaptacao = ?1", tipoCaptacao).list();
    }
}
