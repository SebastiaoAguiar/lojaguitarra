package br.unitins.lojaguitarra.repository;

import java.util.List;

import br.unitins.lojaguitarra.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public List<Cliente> findByNome(String nome) {
        return find("upper(nome) LIKE upper(?1)", "%" + nome + "%").list();
    }

    public boolean existsByMunicipio(Long idMunicipio) {
        Long total = getEntityManager()
                .createQuery("select count(c) from Cliente c join c.enderecos e where e.municipio.id = :id", Long.class)
                .setParameter("id", idMunicipio)
                .getSingleResult();
        return total > 0;
    }
}
