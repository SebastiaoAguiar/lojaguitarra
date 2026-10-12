package br.unitins.lojaguitarra.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class GuitarraEletrica extends Guitarra {

    @Enumerated(EnumType.STRING)
    private ConfiguracaoCaptadores configuracaoCaptadores;

    private Boolean captacaoAtiva;

    @Enumerated(EnumType.STRING)
    private TipoPonte tipoPonte;

    // associacao N:N: um modelo de captador pode equipar varias guitarras eletricas
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "guitarra_captador",
            joinColumns = @JoinColumn(name = "id_guitarra"),
            inverseJoinColumns = @JoinColumn(name = "id_captador"))
    private List<Captador> captadores = new ArrayList<>();

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

    public List<Captador> getCaptadores() {
        return captadores;
    }

    public void setCaptadores(List<Captador> captadores) {
        this.captadores = captadores;
    }
}
