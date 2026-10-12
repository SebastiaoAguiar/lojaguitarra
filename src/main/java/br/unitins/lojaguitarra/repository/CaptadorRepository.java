package br.unitins.lojaguitarra.repository;

import java.util.List;

import br.unitins.lojaguitarra.model.Captador;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CaptadorRepository implements PanacheRepository<Captador> {

    public List<Captador> findByNome(String nome) {
        return find("upper(nome) LIKE upper(?1)", "%" + nome + "%").list();
    }
}
