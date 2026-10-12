package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.exception.BusinessValidationException;
import br.unitins.lojaguitarra.exception.ViolationDetail;
import br.unitins.lojaguitarra.model.Marca;
import br.unitins.lojaguitarra.repository.GuitarraRepository;
import br.unitins.lojaguitarra.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class MarcaServiceImpl implements MarcaService {

    @Inject
    MarcaRepository repository;

    @Inject
    GuitarraRepository guitarraRepository;

    @Override
    @Transactional
    public Marca create(Marca marca) {
        repository.persist(marca);
        return marca;
    }

    @Override
    @Transactional
    public void update(Long id, Marca marca) {
        Marca novaMarca = repository.findById(id);
        if (novaMarca == null) {
            throw new NotFoundException("Marca nao encontrada.");
        }
        novaMarca.setNome(marca.getNome());
        novaMarca.setPaisOrigem(marca.getPaisOrigem());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (repository.findById(id) == null) {
            throw new NotFoundException("Marca nao encontrada.");
        }
        // agregacao: a marca so pode sair se nenhuma guitarra depender dela
        if (guitarraRepository.existsByMarca(id)) {
            throw new BusinessValidationException(
                    Response.Status.CONFLICT,
                    "Nao e possivel excluir a marca.",
                    List.of(new ViolationDetail("id", "Existem guitarras cadastradas com esta marca.")));
        }
        repository.deleteById(id);
    }

    @Override
    public Marca findById(Long id) {
        Marca marca = repository.findById(id);
        if (marca == null) {
            throw new NotFoundException("Marca nao encontrada.");
        }
        return marca;
    }

    @Override
    public List<Marca> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public List<Marca> findAll() {
        return repository.listAll();
    }
}
