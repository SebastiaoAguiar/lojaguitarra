package br.unitins.lojaguitarra.repository;

import java.util.List;

import br.unitins.lojaguitarra.model.Estado;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EstadoRepository implements PanacheRepository<Estado> {

    public List<Estado> findByNome(String nome) {
        return find("upper(nome) LIKE upper(?1)", "%" + nome + "%").list();
    }

    public boolean existsBySigla(String sigla) {
        return count("upper(sigla) = upper(?1)", sigla) > 0;
    }

    public boolean existsBySiglaAndIdNot(String sigla, Long id) {
        return count("upper(sigla) = upper(?1) and id <> ?2", sigla, id) > 0;
    }
}
