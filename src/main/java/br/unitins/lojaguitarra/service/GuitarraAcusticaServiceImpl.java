package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraAcusticaDTO;
import br.unitins.lojaguitarra.dto.GuitarraAcusticaResponseDTO;
import br.unitins.lojaguitarra.model.GuitarraAcustica;
import br.unitins.lojaguitarra.model.Marca;
import br.unitins.lojaguitarra.model.TipoCorda;
import br.unitins.lojaguitarra.repository.GuitarraAcusticaRepository;
import br.unitins.lojaguitarra.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GuitarraAcusticaServiceImpl implements GuitarraAcusticaService {

    @Inject
    GuitarraAcusticaRepository repository;

    @Inject
    MarcaRepository marcaRepository;

    @Override
    @Transactional
    public GuitarraAcusticaResponseDTO create(GuitarraAcusticaDTO dto) {
        GuitarraAcustica guitarra = new GuitarraAcustica();
        preencher(guitarra, dto);
        repository.persist(guitarra);
        return GuitarraAcusticaResponseDTO.fromEntity(guitarra);
    }

    @Override
    @Transactional
    public void update(Long id, GuitarraAcusticaDTO dto) {
        GuitarraAcustica guitarra = repository.findById(id);
        if (guitarra == null) {
            throw new NotFoundException("Guitarra acústica não encontrada");
        }
        preencher(guitarra, dto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Guitarra acústica não encontrada");
        }
    }

    @Override
    public GuitarraAcusticaResponseDTO findById(Long id) {
        GuitarraAcustica guitarra = repository.findById(id);
        if (guitarra == null) {
            throw new NotFoundException("Guitarra acústica não encontrada");
        }
        return GuitarraAcusticaResponseDTO.fromEntity(guitarra);
    }

    @Override
    public List<GuitarraAcusticaResponseDTO> findByNome(String nome) {
        return toResponse(repository.findByNome(nome));
    }

    @Override
    public List<GuitarraAcusticaResponseDTO> findByTipoCorda(TipoCorda tipoCorda) {
        return toResponse(repository.findByTipoCorda(tipoCorda));
    }

    @Override
    public List<GuitarraAcusticaResponseDTO> findAll() {
        return toResponse(repository.listAll());
    }

    // copia os dados do DTO para a entidade, buscando a Marca pelo id
    private void preencher(GuitarraAcustica guitarra, GuitarraAcusticaDTO dto) {
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
        guitarra.setTipoCorda(dto.tipoCorda());
        guitarra.setCutaway(dto.cutaway());
    }

    private List<GuitarraAcusticaResponseDTO> toResponse(List<GuitarraAcustica> guitarras) {
        return guitarras.stream().map(GuitarraAcusticaResponseDTO::fromEntity).toList();
    }
}
