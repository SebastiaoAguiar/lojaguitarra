package br.unitins.lojaguitarra.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// abstrata: toda guitarra da loja e eletrica, acustica ou eletroacustica
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Guitarra extends DefaultEntity {

    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;

    private String modelo;
    private String cor;
    private Double preco;

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
}
