package br.unitins.lojaguitarra.repository;

import br.unitins.lojaguitarra.model.Pessoa;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

// usado para validar o CPF em todo o tipo de pessoa (cliente ou luthier)
@ApplicationScoped
public class PessoaRepository implements PanacheRepository<Pessoa> {

    public boolean existsByCpf(String cpf) {
        return count("cpf = ?1", cpf) > 0;
    }

    public boolean existsByCpfAndIdNot(String cpf, Long id) {
        return count("cpf = ?1 and id <> ?2", cpf, id) > 0;
    }
}
