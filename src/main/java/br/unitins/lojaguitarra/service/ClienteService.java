package br.unitins.lojaguitarra.service;

import java.util.List;

import br.unitins.lojaguitarra.model.Cliente;

public interface ClienteService {
    Cliente create(Cliente cliente);
    void update(Long id, Cliente cliente);
    void delete(Long id);
    Cliente findById(Long id);
    List<Cliente> findByNome(String nome);
    List<Cliente> findAll();
}
