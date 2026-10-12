package br.unitins.lojaguitarra.service;

import java.util.ArrayList;
import java.util.List;

import br.unitins.lojaguitarra.model.GuitarraEletrica;
import br.unitins.lojaguitarra.model.TipoPonte;
import br.unitins.lojaguitarra.repository.GuitarraEletricaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GuitarraEletricaServiceImpl implements GuitarraEletricaService {

    @Inject
    GuitarraEletricaRepository repository;

    @Override
    @Transactional
    public GuitarraEletrica create(GuitarraEletrica guitarra) {
        repository.persist(guitarra);
        return guitarra;
    }

    @Override
    @Transactional
    public void update(Long id, GuitarraEletrica guitarra) {
        GuitarraEletrica novaGuitarra = repository.findById(id);
        if (novaGuitarra == null) {
            throw new NotFoundException("Guitarra eletrica nao encontrada.");
        }
        novaGuitarra.setNome(guitarra.getNome());
        novaGuitarra.setMarca(guitarra.getMarca());
        novaGuitarra.setModelo(guitarra.getModelo());
        novaGuitarra.setCor(guitarra.getCor());
        novaGuitarra.setPreco(guitarra.getPreco());
        // composicao: atualiza a especificacao existente em vez de criar outra
        novaGuitarra.getEspecificacao().setFormatoCorpo(guitarra.getEspecificacao().getFormatoCorpo());
        novaGuitarra.getEspecificacao().setMadeiraCorpo(guitarra.getEspecificacao().getMadeiraCorpo());
        novaGuitarra.getEspecificacao().setMadeiraEscala(guitarra.getEspecificacao().getMadeiraEscala());
        novaGuitarra.getEspecificacao().setNumeroTrastes(guitarra.getEspecificacao().getNumeroTrastes());
        novaGuitarra.setConfiguracaoCaptadores(guitarra.getConfiguracaoCaptadores());
        novaGuitarra.setCaptacaoAtiva(guitarra.getCaptacaoAtiva());
        novaGuitarra.setTipoPonte(guitarra.getTipoPonte());
        novaGuitarra.setCaptadores(new ArrayList<>(guitarra.getCaptadores()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Guitarra eletrica nao encontrada.");
        }
    }

    @Override
    public GuitarraEletrica findById(Long id) {
        GuitarraEletrica guitarra = repository.findById(id);
        if (guitarra == null) {
            throw new NotFoundException("Guitarra eletrica nao encontrada.");
        }
        return guitarra;
    }

    @Override
    public List<GuitarraEletrica> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public List<GuitarraEletrica> findByTipoPonte(TipoPonte tipoPonte) {
        return repository.findByTipoPonte(tipoPonte);
    }

    @Override
    public List<GuitarraEletrica> findAll() {
        return repository.listAll();
    }
}
