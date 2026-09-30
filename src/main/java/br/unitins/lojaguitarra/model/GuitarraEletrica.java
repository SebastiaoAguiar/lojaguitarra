package br.unitins.lojaguitarra.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class GuitarraEletrica extends Guitarra {

    @Enumerated(EnumType.STRING)
    private ConfiguracaoCaptadores configuracaoCaptadores;

    private Boolean captacaoAtiva;

    @Enumerated(EnumType.STRING)
    private TipoPonte tipoPonte;

    public ConfiguracaoCaptadores getConfiguracaoCaptadores() {
        return configuracaoCaptadores;
    }
    public void setConfiguracaoCaptadores(ConfiguracaoCaptadores configuracaoCaptadores) {
        this.configuracaoCaptadores = configuracaoCaptadores;
    }
    public Boolean getCaptacaoAtiva() {
        return captacaoAtiva;
    }
    public void setCaptacaoAtiva(Boolean captacaoAtiva) {
        this.captacaoAtiva = captacaoAtiva;
    }
    public TipoPonte getTipoPonte() {
        return tipoPonte;
    }
    public void setTipoPonte(TipoPonte tipoPonte) {
        this.tipoPonte = tipoPonte;
    }
}
