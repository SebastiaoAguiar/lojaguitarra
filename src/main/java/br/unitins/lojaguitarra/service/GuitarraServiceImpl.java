package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraResponseDTO;
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

    private List<GuitarraResponseDTO> toResponse(List<Guitarra> guitarras) {
        return guitarras.stream().map(GuitarraResponseDTO::fromEntity).toList();
    }
}
