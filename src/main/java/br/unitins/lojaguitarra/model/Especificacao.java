package br.unitins.lojaguitarra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

// composicao: so existe dentro de uma Guitarra (nao tem CRUD proprio)
@Entity
public class Especificacao extends DefaultEntity {

    @Column(name = "formato_corpo")
    private String formatoCorpo;

    @Column(name = "madeira_corpo")
    private String madeiraCorpo;

    @Column(name = "madeira_escala")
    private String madeiraEscala;

    @Column(name = "numero_trastes")
    private Integer numeroTrastes;

    public String getFormatoCorpo() {
        return formatoCorpo;
    }

    public void setFormatoCorpo(String formatoCorpo) {
        this.formatoCorpo = formatoCorpo;
    }

    public String getMadeiraCorpo() {
        return madeiraCorpo;
    }

    public void setMadeiraCorpo(String madeiraCorpo) {
        this.madeiraCorpo = madeiraCorpo;
    }

    public String getMadeiraEscala() {
        return madeiraEscala;
    }

    public void setMadeiraEscala(String madeiraEscala) {
        this.madeiraEscala = madeiraEscala;
    }

    public Integer getNumeroTrastes() {
        return numeroTrastes;
    }

    public void setNumeroTrastes(Integer numeroTrastes) {
        this.numeroTrastes = numeroTrastes;
    }
}
