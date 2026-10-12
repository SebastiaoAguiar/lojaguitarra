package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.model.GuitarraAcustica;
import br.unitins.lojaguitarra.model.TipoCorda;
import br.unitins.lojaguitarra.repository.GuitarraAcusticaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GuitarraAcusticaServiceImpl implements GuitarraAcusticaService {

    @Inject
    GuitarraAcusticaRepository repository;

    @Override
    @Transactional
    public GuitarraAcustica create(GuitarraAcustica guitarra) {
        repository.persist(guitarra);
        return guitarra;
    }

    @Override
    @Transactional
    public void update(Long id, GuitarraAcustica guitarra) {
        GuitarraAcustica novaGuitarra = repository.findById(id);
        if (novaGuitarra == null) {
            throw new NotFoundException("Guitarra acustica nao encontrada.");
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
        novaGuitarra.setTipoTampo(guitarra.getTipoTampo());
        novaGuitarra.setTipoCorda(guitarra.getTipoCorda());
        novaGuitarra.setCutaway(guitarra.getCutaway());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Guitarra acustica nao encontrada.");
        }
    }

    @Override
    public GuitarraAcustica findById(Long id) {
        GuitarraAcustica guitarra = repository.findById(id);
        if (guitarra == null) {
            throw new NotFoundException("Guitarra acustica nao encontrada.");
        }
        return guitarra;
    }

    @Override
    public List<GuitarraAcustica> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public List<GuitarraAcustica> findByTipoCorda(TipoCorda tipoCorda) {
        return repository.findByTipoCorda(tipoCorda);
    }

    @Override
    public List<GuitarraAcustica> findAll() {
        return repository.listAll();
    }
}
