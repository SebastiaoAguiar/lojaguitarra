package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraEletroacusticaDTO;
import br.unitins.lojaguitarra.dto.GuitarraEletroacusticaResponseDTO;
import br.unitins.lojaguitarra.model.GuitarraEletroacustica;
import br.unitins.lojaguitarra.model.Marca;
import br.unitins.lojaguitarra.model.TipoCaptacao;
import br.unitins.lojaguitarra.repository.GuitarraEletroacusticaRepository;
import br.unitins.lojaguitarra.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GuitarraEletroacusticaServiceImpl implements GuitarraEletroacusticaService {

    @Inject
    GuitarraEletroacusticaRepository repository;

    @Inject
    MarcaRepository marcaRepository;

    @Override
    @Transactional
    public GuitarraEletroacusticaResponseDTO create(GuitarraEletroacusticaDTO dto) {
        GuitarraEletroacustica guitarra = new GuitarraEletroacustica();
        preencher(guitarra, dto);
        repository.persist(guitarra);
        return GuitarraEletroacusticaResponseDTO.fromEntity(guitarra);
    }

    @Override
    @Transactional
    public void update(Long id, GuitarraEletroacusticaDTO dto) {
        GuitarraEletroacustica guitarra = repository.findById(id);
        if (guitarra == null) {
            throw new NotFoundException("Guitarra eletroacústica não encontrada");
        }
        preencher(guitarra, dto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Guitarra eletroacústica não encontrada");
        }
    }

    @Override
    public GuitarraEletroacusticaResponseDTO findById(Long id) {
        GuitarraEletroacustica guitarra = repository.findById(id);
        if (guitarra == null) {
            throw new NotFoundException("Guitarra eletroacústica não encontrada");
        }
        return GuitarraEletroacusticaResponseDTO.fromEntity(guitarra);
    }

    @Override
    public List<GuitarraEletroacusticaResponseDTO> findByNome(String nome) {
        return toResponse(repository.findByNome(nome));
    }

    @Override
    public List<GuitarraEletroacusticaResponseDTO> findByTipoCaptacao(TipoCaptacao tipoCaptacao) {
        return toResponse(repository.findByTipoCaptacao(tipoCaptacao));
    }

    @Override
    public List<GuitarraEletroacusticaResponseDTO> findAll() {
        return toResponse(repository.listAll());
    }

    // copia os dados do DTO para a entidade, buscando a Marca pelo id
    private void preencher(GuitarraEletroacustica guitarra, GuitarraEletroacusticaDTO dto) {
        Marca marca = marcaRepository.findById(dto.idMarca());
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada");
        }
        guitarra.setNome(dto.nome());
        guitarra.setMarca(marca);
        guitarra.setModelo(dto.modelo());
        guitarra.setCor(dto.cor());
        guitarra.setPreco(dto.preco());
        guitarra.setTipoTampo(dto.tipoTampo());
        guitarra.setCutaway(dto.cutaway());
        guitarra.setTipoCaptacao(dto.tipoCaptacao());
        guitarra.setAfinadorEmbutido(dto.afinadorEmbutido());
        guitarra.setBandasEqualizador(dto.bandasEqualizador());
    }

    private List<GuitarraEletroacusticaResponseDTO> toResponse(List<GuitarraEletroacustica> guitarras) {
        return guitarras.stream().map(GuitarraEletroacusticaResponseDTO::fromEntity).toList();
    }
}
