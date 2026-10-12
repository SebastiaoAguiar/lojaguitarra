package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.exception.BusinessValidationException;
import br.unitins.lojaguitarra.exception.ViolationDetail;
import br.unitins.lojaguitarra.model.Estado;
import br.unitins.lojaguitarra.repository.EstadoRepository;
import br.unitins.lojaguitarra.repository.MunicipioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {

    @Inject
    EstadoRepository repository;

    @Inject
    MunicipioRepository municipioRepository;

    @Override
    @Transactional
    public Estado create(Estado estado) {
        validarSiglaDuplicada(estado.getSigla(), null);
        repository.persist(estado);
        return estado;
    }

    @Override
    @Transactional
    public void update(Long id, Estado estado) {
        Estado novoEstado = repository.findById(id);
        if (novoEstado == null) {
            throw new NotFoundException("Estado nao encontrado.");
        }
        validarSiglaDuplicada(estado.getSigla(), id);
        novoEstado.setNome(estado.getNome());
        novoEstado.setSigla(estado.getSigla());
        novoEstado.setRegiao(estado.getRegiao());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (repository.findById(id) == null) {
            throw new NotFoundException("Estado nao encontrado.");
        }
        if (municipioRepository.existsByEstado(id)) {
            throw new BusinessValidationException(
                    Response.Status.CONFLICT,
                    "Nao e possivel excluir o estado.",
                    List.of(new ViolationDetail("id", "Existem municipios cadastrados neste estado.")));
        }
        repository.deleteById(id);
    }

    @Override
    public Estado findById(Long id) {
        Estado estado = repository.findById(id);
        if (estado == null) {
            throw new NotFoundException("Estado nao encontrado.");
        }
        return estado;
    }

    @Override
    public List<Estado> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public List<Estado> findAll() {
        return repository.listAll();
    }

    private void validarSiglaDuplicada(String sigla, Long idIgnorado) {
        boolean siglaDuplicada = idIgnorado == null
                ? repository.existsBySigla(sigla)
                : repository.existsBySiglaAndIdNot(sigla, idIgnorado);

        if (siglaDuplicada) {
            throw new BusinessValidationException(
                    Response.Status.CONFLICT,
                    "Um ou mais campos da requisicao sao invalidos.",
                    List.of(new ViolationDetail("sigla", "Ja existe um estado cadastrado com a sigla informada.")));
        }
    }
}
