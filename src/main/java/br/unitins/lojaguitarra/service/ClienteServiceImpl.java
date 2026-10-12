package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.exception.BusinessValidationException;
import br.unitins.lojaguitarra.exception.ViolationDetail;
import br.unitins.lojaguitarra.model.Cliente;
import br.unitins.lojaguitarra.repository.ClienteRepository;
import br.unitins.lojaguitarra.repository.PessoaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository repository;

    @Inject
    PessoaRepository pessoaRepository;

    @Override
    @Transactional
    public Cliente create(Cliente cliente) {
        validarCpfDuplicado(cliente.getCpf(), null);
        repository.persist(cliente);
        return cliente;
    }

    @Override
    @Transactional
    public void update(Long id, Cliente cliente) {
        Cliente novoCliente = repository.findById(id);
        if (novoCliente == null) {
            throw new NotFoundException("Cliente nao encontrado.");
        }
        validarCpfDuplicado(cliente.getCpf(), id);
        novoCliente.setNome(cliente.getNome());
        novoCliente.setCpf(cliente.getCpf());
        novoCliente.setEmail(cliente.getEmail());
        novoCliente.setTelefone(cliente.getTelefone());

        // composicao: troca os enderecos na mesma colecao para o orphanRemoval apagar os antigos
        novoCliente.getEnderecos().clear();
        novoCliente.getEnderecos().addAll(cliente.getEnderecos());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Cliente nao encontrado.");
        }
    }

    @Override
    public Cliente findById(Long id) {
        Cliente cliente = repository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente nao encontrado.");
        }
        return cliente;
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public List<Cliente> findAll() {
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
