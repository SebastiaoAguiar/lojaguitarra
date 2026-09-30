package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.dto.GuitarraEletricaDTO;
import br.unitins.lojaguitarra.dto.GuitarraEletricaResponseDTO;
import br.unitins.lojaguitarra.model.GuitarraEletrica;
import br.unitins.lojaguitarra.model.Marca;
import br.unitins.lojaguitarra.model.TipoPonte;
import br.unitins.lojaguitarra.repository.GuitarraEletricaRepository;
import br.unitins.lojaguitarra.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GuitarraEletricaServiceImpl implements GuitarraEletricaService {

    @Inject
    GuitarraEletricaRepository repository;

    @Inject
    MarcaRepository marcaRepository;

    @Override
    @Transactional
    public GuitarraEletricaResponseDTO create(GuitarraEletricaDTO dto) {
        GuitarraEletrica guitarra = new GuitarraEletrica();
        preencher(guitarra, dto);
        repository.persist(guitarra);
        return GuitarraEletricaResponseDTO.fromEntity(guitarra);
    }

    @Override
    @Transactional
    public void update(Long id, GuitarraEletricaDTO dto) {
        GuitarraEletrica guitarra = repository.findById(id);
        if (guitarra == null) {
            throw new NotFoundException("Guitarra elétrica não encontrada");
        }
        preencher(guitarra, dto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Guitarra elétrica não encontrada");
        }
    }

    @Override
    public GuitarraEletricaResponseDTO findById(Long id) {
        GuitarraEletrica guitarra = repository.findById(id);
        if (guitarra == null) {
            throw new NotFoundException("Guitarra elétrica não encontrada");
        }
        return GuitarraEletricaResponseDTO.fromEntity(guitarra);
    }

    @Override
    public List<GuitarraEletricaResponseDTO> findByNome(String nome) {
        return toResponse(repository.findByNome(nome));
    }

    @Override
    public List<GuitarraEletricaResponseDTO> findByTipoPonte(TipoPonte tipoPonte) {
        return toResponse(repository.findByTipoPonte(tipoPonte));
    }

    @Override
    public List<GuitarraEletricaResponseDTO> findAll() {
        return toResponse(repository.listAll());
    }

    // copia os dados do DTO para a entidade, buscando a Marca pelo id
    private void preencher(GuitarraEletrica guitarra, GuitarraEletricaDTO dto) {
        Marca marca = marcaRepository.findById(dto.idMarca());
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada");
        }
        guitarra.setNome(dto.nome());
        guitarra.setMarca(marca);
        guitarra.setModelo(dto.modelo());
        guitarra.setCor(dto.cor());
        guitarra.setPreco(dto.preco());
        guitarra.setConfiguracaoCaptadores(dto.configuracaoCaptadores());
        guitarra.setCaptacaoAtiva(dto.captacaoAtiva());
        guitarra.setTipoPonte(dto.tipoPonte());
    }

    private List<GuitarraEletricaResponseDTO> toResponse(List<GuitarraEletrica> guitarras) {
        return guitarras.stream().map(GuitarraEletricaResponseDTO::fromEntity).toList();
    }
}
