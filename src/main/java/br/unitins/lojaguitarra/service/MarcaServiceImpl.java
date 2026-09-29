package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.dto.MarcaDTO;
import br.unitins.lojaguitarra.dto.MarcaResponseDTO;
import br.unitins.lojaguitarra.model.Marca;
import br.unitins.lojaguitarra.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class MarcaServiceImpl implements MarcaService {

    @Inject
    MarcaRepository repository;

    @Override
    @Transactional
    public MarcaResponseDTO create(MarcaDTO dto) {
        Marca marca = new Marca();
        marca.setNome(dto.nome());
        repository.persist(marca);
        return MarcaResponseDTO.fromEntity(marca);
    }

    @Override
    @Transactional
    public void update(Long id, MarcaDTO dto) {
        Marca marca = repository.findById(id);
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada");
        }
        marca.setNome(dto.nome());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Marca não encontrada");
        }
    }

    @Override
    public MarcaResponseDTO findById(Long id) {
        Marca marca = repository.findById(id);
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada");
        }
        return MarcaResponseDTO.fromEntity(marca);
    }

    @Override
    public List<MarcaResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream().map(MarcaResponseDTO::fromEntity).toList();
    }

    @Override
    public List<MarcaResponseDTO> findAll() {
        return repository.listAll().stream().map(MarcaResponseDTO::fromEntity).toList();
    }
}
