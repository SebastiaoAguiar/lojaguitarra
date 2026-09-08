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

    public List<Guitarra> findByMarca(String marca) {
        return find("upper(marca) LIKE upper(?1)", "%" + marca + "%").list();
    }

    public List<Guitarra> findByModelo(String modelo) {
        return find("upper(modelo) LIKE upper(?1)", "%" + modelo + "%").list();
    }

    public List<Guitarra> findByCor(String cor) {
        return find("upper(cor) LIKE upper(?1)", "%" + cor + "%").list();
    }

    public List<Guitarra> findByPreco(Double min, Double max) {
        return find("preco BETWEEN ?1 AND ?2", min, max).list();
    }
}
