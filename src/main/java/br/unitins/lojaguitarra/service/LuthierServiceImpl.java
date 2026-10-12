package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.exception.BusinessValidationException;
import br.unitins.lojaguitarra.exception.ViolationDetail;
import br.unitins.lojaguitarra.model.Luthier;
import br.unitins.lojaguitarra.repository.LuthierRepository;
import br.unitins.lojaguitarra.repository.PessoaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class LuthierServiceImpl implements LuthierService {

    @Inject
    LuthierRepository repository;

    @Inject
    PessoaRepository pessoaRepository;

    @Override
    @Transactional
    public Luthier create(Luthier luthier) {
        validarCpfDuplicado(luthier.getCpf(), null);
        repository.persist(luthier);
        return luthier;
    }

    @Override
    @Transactional
    public void update(Long id, Luthier luthier) {
        Luthier novoLuthier = repository.findById(id);
        if (novoLuthier == null) {
            throw new NotFoundException("Luthier nao encontrado.");
        }
        validarCpfDuplicado(luthier.getCpf(), id);
        novoLuthier.setNome(luthier.getNome());
        novoLuthier.setCpf(luthier.getCpf());
        novoLuthier.setEmail(luthier.getEmail());
        novoLuthier.setEspecialidade(luthier.getEspecialidade());
        novoLuthier.setAnosExperiencia(luthier.getAnosExperiencia());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Luthier nao encontrado.");
        }
    }

    @Override
    public Luthier findById(Long id) {
        Luthier luthier = repository.findById(id);
        if (luthier == null) {
            throw new NotFoundException("Luthier nao encontrado.");
        }
        return luthier;
    }

    @Override
    public List<Luthier> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public List<Luthier> findByEspecialidade(String especialidade) {
        return repository.findByEspecialidade(especialidade);
    }

    @Override
    public List<Luthier> findAll() {
        return repository.listAll();
    }

    private void validarCpfDuplicado(String cpf, Long idIgnorado) {
        boolean cpfDuplicado = idIgnorado == null
                ? pessoaRepository.existsByCpf(cpf)
                : pessoaRepository.existsByCpfAndIdNot(cpf, idIgnorado);

        if (cpfDuplicado) {
            throw new BusinessValidationException(
                    Response.Status.CONFLICT,
                    "Um ou mais campos da requisicao sao invalidos.",
                    List.of(new ViolationDetail("cpf", "Ja existe uma pessoa cadastrada com o CPF informado.")));
        }
    }
}
