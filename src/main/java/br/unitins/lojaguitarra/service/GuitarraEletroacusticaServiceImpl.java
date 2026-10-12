package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.model.GuitarraEletroacustica;
import br.unitins.lojaguitarra.model.TipoCaptacao;
import br.unitins.lojaguitarra.repository.GuitarraEletroacusticaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GuitarraEletroacusticaServiceImpl implements GuitarraEletroacusticaService {

    @Inject
    GuitarraEletroacusticaRepository repository;

    @Override
    @Transactional
    public GuitarraEletroacustica create(GuitarraEletroacustica guitarra) {
        repository.persist(guitarra);
        return guitarra;
    }

    @Override
    @Transactional
    public void update(Long id, GuitarraEletroacustica guitarra) {
        GuitarraEletroacustica novaGuitarra = repository.findById(id);
        if (novaGuitarra == null) {
            throw new NotFoundException("Guitarra eletroacustica nao encontrada.");
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
        novaGuitarra.setCutaway(guitarra.getCutaway());
        novaGuitarra.setTipoCaptacao(guitarra.getTipoCaptacao());
        novaGuitarra.setAfinadorEmbutido(guitarra.getAfinadorEmbutido());
        novaGuitarra.setBandasEqualizador(guitarra.getBandasEqualizador());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Guitarra eletroacustica nao encontrada.");
        }
    }

    @Override
    public GuitarraEletroacustica findById(Long id) {
        GuitarraEletroacustica guitarra = repository.findById(id);
        if (guitarra == null) {
            throw new NotFoundException("Guitarra eletroacustica nao encontrada.");
        }
        return guitarra;
    }

    @Override
    public List<GuitarraEletroacustica> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public List<GuitarraEletroacustica> findByTipoCaptacao(TipoCaptacao tipoCaptacao) {
        return repository.findByTipoCaptacao(tipoCaptacao);
    }

    @Override
    public List<GuitarraEletroacustica> findAll() {
        return repository.listAll();
    }
}
