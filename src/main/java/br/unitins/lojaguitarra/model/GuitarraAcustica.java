package br.unitins.lojaguitarra.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class GuitarraAcustica extends Guitarra {

    @Enumerated(EnumType.STRING)
    private TipoTampo tipoTampo;

    @Enumerated(EnumType.STRING)
    private TipoCorda tipoCorda;

    private Boolean cutaway;

    public TipoTampo getTipoTampo() {
        return tipoTampo;
    }
    public void setTipoTampo(TipoTampo tipoTampo) {
        this.tipoTampo = tipoTampo;
    }
    public TipoCorda getTipoCorda() {
        return tipoCorda;
    }
    public void setTipoCorda(TipoCorda tipoCorda) {
        this.tipoCorda = tipoCorda;
    }
    public Boolean getCutaway() {
        return cutaway;
    }
    public void setCutaway(Boolean cutaway) {
        this.cutaway = cutaway;
    }
}
