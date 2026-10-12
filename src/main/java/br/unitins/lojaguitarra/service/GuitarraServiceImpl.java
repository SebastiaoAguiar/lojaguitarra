package br.unitins.lojaguitarra.service;

import java.util.ArrayList;
import java.util.List;

import br.unitins.lojaguitarra.model.Guitarra;
import br.unitins.lojaguitarra.repository.GuitarraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

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
        Guitarra novaGuitarra = repository.findById(id);
        if (novaGuitarra == null) {
            throw new NotFoundException("Guitarra nao encontrada.");
        }
        novaGuitarra.setNome(guitarra.getNome());
        novaGuitarra.setMarca(guitarra.getMarca());
        novaGuitarra.setModelo(guitarra.getModelo());
        novaGuitarra.setCor(guitarra.getCor());
        novaGuitarra.setPreco(guitarra.getPreco());
        novaGuitarra.setCaptadores(new ArrayList<>(guitarra.getCaptadores()));

        // composicao: atualiza a especificacao existente em vez de criar outra
        novaGuitarra.getEspecificacao().setFormatoCorpo(guitarra.getEspecificacao().getFormatoCorpo());
        novaGuitarra.getEspecificacao().setMadeiraCorpo(guitarra.getEspecificacao().getMadeiraCorpo());
        novaGuitarra.getEspecificacao().setMadeiraEscala(guitarra.getEspecificacao().getMadeiraEscala());
        novaGuitarra.getEspecificacao().setNumeroTrastes(guitarra.getEspecificacao().getNumeroTrastes());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Guitarra nao encontrada.");
        }
    }

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
