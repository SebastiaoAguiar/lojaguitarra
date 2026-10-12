package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.exception.BusinessValidationException;
import br.unitins.lojaguitarra.exception.ViolationDetail;
import br.unitins.lojaguitarra.model.Municipio;
import br.unitins.lojaguitarra.repository.ClienteRepository;
import br.unitins.lojaguitarra.repository.MunicipioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class MunicipioServiceImpl implements MunicipioService {

    @Inject
    MunicipioRepository repository;

    @Inject
    ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Municipio create(Municipio municipio) {
        repository.persist(municipio);
        return municipio;
    }

    @Override
    @Transactional
    public void update(Long id, Municipio municipio) {
        Municipio novoMunicipio = repository.findById(id);
        if (novoMunicipio == null) {
            throw new NotFoundException("Municipio nao encontrado.");
        }
        novoMunicipio.setNome(municipio.getNome());
        novoMunicipio.setEstado(municipio.getEstado());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (repository.findById(id) == null) {
            throw new NotFoundException("Municipio nao encontrado.");
        }
        if (clienteRepository.existsByMunicipio(id)) {
            throw new BusinessValidationException(
                    Response.Status.CONFLICT,
                    "Nao e possivel excluir o municipio.",
                    List.of(new ViolationDetail("id", "Existem clientes com endereco neste municipio.")));
        }
        repository.deleteById(id);
    }

    @Override
    public Municipio findById(Long id) {
        Municipio municipio = repository.findById(id);
        if (municipio == null) {
            throw new NotFoundException("Municipio nao encontrado.");
        }
        return municipio;
    }

    @Override
    public List<Municipio> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public List<Municipio> findAll() {
        return repository.listAll();
    }
}
