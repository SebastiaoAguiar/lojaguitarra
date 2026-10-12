package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.exception.BusinessValidationException;
import br.unitins.lojaguitarra.exception.ViolationDetail;
import br.unitins.lojaguitarra.model.Captador;
import br.unitins.lojaguitarra.repository.CaptadorRepository;
import br.unitins.lojaguitarra.repository.GuitarraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class CaptadorServiceImpl implements CaptadorService {

    @Inject
    CaptadorRepository repository;

    @Inject
    GuitarraRepository guitarraRepository;

    @Override
    @Transactional
    public Captador create(Captador captador) {
        repository.persist(captador);
        return captador;
    }

    @Override
    @Transactional
    public void update(Long id, Captador captador) {
        Captador novoCaptador = repository.findById(id);
        if (novoCaptador == null) {
            throw new NotFoundException("Captador nao encontrado.");
        }
        novoCaptador.setNome(captador.getNome());
        novoCaptador.setTipo(captador.getTipo());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (repository.findById(id) == null) {
            throw new NotFoundException("Captador nao encontrado.");
        }
        if (guitarraRepository.existsByCaptador(id)) {
            throw new BusinessValidationException(
                    Response.Status.CONFLICT,
                    "Nao e possivel excluir o captador.",
                    List.of(new ViolationDetail("id", "Existem guitarras equipadas com este captador.")));
        }
        repository.deleteById(id);
    }

    @Override
    public Captador findById(Long id) {
        Captador captador = repository.findById(id);
        if (captador == null) {
            throw new NotFoundException("Captador nao encontrado.");
        }
        return captador;
    }

    @Override
    public List<Captador> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public List<Captador> findAll() {
        return repository.listAll();
    }
}
