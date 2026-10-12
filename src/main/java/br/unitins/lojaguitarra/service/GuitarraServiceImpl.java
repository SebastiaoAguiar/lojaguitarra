package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.model.Guitarra;
import br.unitins.lojaguitarra.repository.GuitarraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GuitarraServiceImpl implements GuitarraService {

    @Inject
    GuitarraRepository repository;

    @Override
    public Guitarra findById(Long id) {
        Guitarra guitarra = repository.findById(id);
        if (guitarra == null) {
            throw new NotFoundException("Guitarra nao encontrada.");
        }
        return guitarra;
    }

    @Override
    public List<Guitarra> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public List<Guitarra> findByMarca(String marca) {
        return repository.findByMarca(marca);
    }

    @Override
    public List<Guitarra> findByModelo(String modelo) {
        return repository.findByModelo(modelo);
    }

    @Override
    public List<Guitarra> findByCor(String cor) {
        return repository.findByCor(cor);
    }

    @Override
    public List<Guitarra> findByPreco(Double min, Double max) {
        return repository.findByPreco(min, max);
    }

    @Override
    public List<Guitarra> findAll() {
        return repository.listAll();
    }
}
