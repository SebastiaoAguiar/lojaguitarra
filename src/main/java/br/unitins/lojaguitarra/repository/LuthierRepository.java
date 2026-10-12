package br.unitins.lojaguitarra.repository;

import java.util.List;

import br.unitins.lojaguitarra.model.Luthier;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LuthierRepository implements PanacheRepository<Luthier> {

    public List<Luthier> findByNome(String nome) {
        return find("upper(nome) LIKE upper(?1)", "%" + nome + "%").list();
    }

    public List<Luthier> findByEspecialidade(String especialidade) {
        return find("upper(especialidade) LIKE upper(?1)", "%" + especialidade + "%").list();
    }
}
