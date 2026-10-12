package br.unitins.lojaguitarra.repository;

import java.util.List;

import br.unitins.lojaguitarra.model.GuitarraAcustica;
import br.unitins.lojaguitarra.model.TipoCorda;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GuitarraAcusticaRepository implements PanacheRepository<GuitarraAcustica> {
    public List<GuitarraAcustica> findByNome(String nome) {
        return find("upper(nome) LIKE upper(?1)", "%" + nome + "%").list();
    }

    public List<GuitarraAcustica> findByTipoCorda(TipoCorda tipoCorda) {
        return find("tipoCorda = ?1", tipoCorda).list();
    }
}
