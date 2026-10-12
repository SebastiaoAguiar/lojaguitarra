package br.unitins.lojaguitarra.repository;

import java.util.List;

import br.unitins.lojaguitarra.model.GuitarraEletrica;
import br.unitins.lojaguitarra.model.TipoPonte;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GuitarraEletricaRepository implements PanacheRepository<GuitarraEletrica> {
    public List<GuitarraEletrica> findByNome(String nome) {
        return find("upper(nome) LIKE upper(?1)", "%" + nome + "%").list();
    }

    public List<GuitarraEletrica> findByTipoPonte(TipoPonte tipoPonte) {
        return find("tipoPonte = ?1", tipoPonte).list();
    }
}
