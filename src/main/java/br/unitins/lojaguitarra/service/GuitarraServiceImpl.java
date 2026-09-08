package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.model.Guitarra;
import br.unitins.lojaguitarra.repository.GuitarraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class GuitarraServiceImpl implements GuitarraService {

    @Inject
    GuitarraRepository repository;

    @Override
    @Transactional
    public Guitarra create(Guitarra guitarra) {
        repository.persist(guitarra);
        return guitarra;
    }

    @Override
    @Transactional
    public void update(Long id, Guitarra guitarra) {
        Guitarra guitarraBanco = repository.findById(id);
        if (guitarraBanco == null) {
            throw new RuntimeException("Guitarra não encontrada");
        }
        guitarraBanco.setNome(guitarra.getNome());
        guitarraBanco.setMarca(guitarra.getMarca());
        guitarraBanco.setModelo(guitarra.getModelo());
        guitarraBanco.setCor(guitarra.getCor());
        guitarraBanco.setPreco(guitarra.getPreco());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Guitarra findById(Long id) {
       return repository.findById(id);
    }

    @Override
    public List<Guitarra> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public List<Guitarra> findAll() {
        return repository.listAll();
    }
    
}
