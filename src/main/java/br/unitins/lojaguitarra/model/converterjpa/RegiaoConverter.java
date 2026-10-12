package br.unitins.lojaguitarra.model.converterjpa;

import br.unitins.lojaguitarra.model.Regiao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RegiaoConverter implements AttributeConverter<Regiao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Regiao regiao) {
        if (regiao == null) {
            return null;
        }
        return regiao.getId();
    }

    @Override
    public Regiao convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }
        return Regiao.fromId(id);
    }
}
