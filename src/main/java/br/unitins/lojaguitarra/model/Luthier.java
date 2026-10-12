package br.unitins.lojaguitarra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Luthier extends Pessoa {

    private String especialidade;

    @Column(name = "anos_experiencia")
    private Integer anosExperiencia;

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Integer getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(Integer anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }
}
