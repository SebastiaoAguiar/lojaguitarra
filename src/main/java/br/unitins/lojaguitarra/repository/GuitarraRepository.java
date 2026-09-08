package br.unitins.lojaguitarra.repository;

import java.util.List;

import br.unitins.lojaguitarra.model.Guitarra;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GuitarraRepository implements PanacheRepository<Guitarra> {
    public List<Guitarra> findByNome(String nome) {
        // return find("SELECT e FROM Guitarra e WHERE e.nome LIKE ?", nome).list();
        return find("upper(nome) LIKE upper(?1)", "%" + nome + "%").list();
    }
}
