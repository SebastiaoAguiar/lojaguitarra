package br.unitins.lojaguitarra.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

// abstrata: toda guitarra da loja e eletrica, acustica ou eletroacustica
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Guitarra extends DefaultEntity {

    private String nome;

    // agregacao: a marca existe independente da guitarra
    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca;

    private String modelo;
    private String cor;
    private Double preco;

    // composicao 1:1: a especificacao nasce e morre com a guitarra
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_especificacao", nullable = false, unique = true)
    private Especificacao especificacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Especificacao getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(Especificacao especificacao) {
        this.especificacao = especificacao;
    }
}
