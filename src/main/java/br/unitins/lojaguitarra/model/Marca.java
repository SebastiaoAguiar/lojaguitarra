package br.unitins.lojaguitarra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Marca extends DefaultEntity {

    private String nome;

    @Column(name = "pais_origem")
    private String paisOrigem;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }
}
