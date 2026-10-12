package br.unitins.lojaguitarra.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class GuitarraEletroacustica extends Guitarra {

    @Enumerated(EnumType.STRING)
    private TipoTampo tipoTampo;

    private Boolean cutaway;

    @Enumerated(EnumType.STRING)
    private TipoCaptacao tipoCaptacao;

    private Boolean afinadorEmbutido;

    private Integer bandasEqualizador;

    public TipoTampo getTipoTampo() {
        return tipoTampo;
    }
    public void setTipoTampo(TipoTampo tipoTampo) {
        this.tipoTampo = tipoTampo;
    }
    public Boolean getCutaway() {
        return cutaway;
    }
    public void setCutaway(Boolean cutaway) {
        this.cutaway = cutaway;
    }
    public TipoCaptacao getTipoCaptacao() {
        return tipoCaptacao;
    }
    public void setTipoCaptacao(TipoCaptacao tipoCaptacao) {
        this.tipoCaptacao = tipoCaptacao;
    }
    public Boolean getAfinadorEmbutido() {
        return afinadorEmbutido;
    }
    public void setAfinadorEmbutido(Boolean afinadorEmbutido) {
        this.afinadorEmbutido = afinadorEmbutido;
    }
    public Integer getBandasEqualizador() {
        return bandasEqualizador;
    }
    public void setBandasEqualizador(Integer bandasEqualizador) {
        this.bandasEqualizador = bandasEqualizador;
    }
}
