package br.unitins.lojaguitarra.repository;

import java.util.List;

import br.unitins.lojaguitarra.model.Guitarra;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GuitarraRepository implements PanacheRepository<Guitarra> {

    public List<Guitarra> findByNome(String nome) {
        return find("upper(nome) LIKE upper(?1)", "%" + nome + "%").list();
    }

    public List<Guitarra> findByMarca(String marca) {
        return find("upper(marca.nome) LIKE upper(?1)", "%" + marca + "%").list();
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

    public boolean existsByMarca(Long idMarca) {
        return count("marca.id = ?1", idMarca) > 0;
    }

    public boolean existsByCaptador(Long idCaptador) {
        Long total = getEntityManager()
                .createQuery("select count(g) from GuitarraEletrica g join g.captadores c where c.id = :id", Long.class)
                .setParameter("id", idCaptador)
                .getSingleResult();
        return total > 0;
    }
}
