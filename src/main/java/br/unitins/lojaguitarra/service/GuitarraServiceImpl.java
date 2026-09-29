package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraDTO;
import br.unitins.lojaguitarra.dto.GuitarraResponseDTO;
import br.unitins.lojaguitarra.model.Guitarra;
import br.unitins.lojaguitarra.model.Marca;
import br.unitins.lojaguitarra.repository.GuitarraRepository;
import br.unitins.lojaguitarra.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GuitarraServiceImpl implements GuitarraService {

    @Inject
    GuitarraRepository repository;

    @Inject
    MarcaRepository marcaRepository;

    @Override
    @Transactional
    public GuitarraResponseDTO create(GuitarraDTO dto) {
        Guitarra guitarra = new Guitarra();
        preencher(guitarra, dto);
        repository.persist(guitarra);
        return GuitarraResponseDTO.fromEntity(guitarra);
    }

    @Override
    @Transactional
    public void update(Long id, GuitarraDTO dto) {
        Guitarra guitarra = repository.findById(id);
        if (guitarra == null) {
            throw new NotFoundException("Guitarra não encontrada");
        }
        preencher(guitarra, dto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Guitarra não encontrada");
        }
    }

    @Override
    public GuitarraResponseDTO findById(Long id) {
        Guitarra guitarra = repository.findById(id);
        if (guitarra == null) {
            throw new NotFoundException("Guitarra não encontrada");
        }
        return GuitarraResponseDTO.fromEntity(guitarra);
    }

    @Override
    public List<GuitarraResponseDTO> findByNome(String nome) {
        return toResponse(repository.findByNome(nome));
    }

    @Override
    public List<GuitarraResponseDTO> findByMarca(String marca) {
        return toResponse(repository.findByMarca(marca));
    }

    @Override
    public List<GuitarraResponseDTO> findByModelo(String modelo) {
        return toResponse(repository.findByModelo(modelo));
    }

    @Override
    public List<GuitarraResponseDTO> findByCor(String cor) {
        return toResponse(repository.findByCor(cor));
    }

    @Override
    public List<GuitarraResponseDTO> findByPreco(Double min, Double max) {
        return toResponse(repository.findByPreco(min, max));
    }

    @Override
    public List<GuitarraResponseDTO> findAll() {
        return toResponse(repository.listAll());
    }

    // copia os dados do DTO para a entidade, buscando a Marca pelo id
    private void preencher(Guitarra guitarra, GuitarraDTO dto) {
        Marca marca = marcaRepository.findById(dto.idMarca());
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada");
        }
        guitarra.setNome(dto.nome());
        guitarra.setMarca(marca);
        guitarra.setModelo(dto.modelo());
        guitarra.setCor(dto.cor());
        guitarra.setPreco(dto.preco());
    }

    private List<GuitarraResponseDTO> toResponse(List<Guitarra> guitarras) {
        return guitarras.stream().map(GuitarraResponseDTO::fromEntity).toList();
    }
}
