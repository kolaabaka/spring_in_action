package com.fx.converter;

import com.fx.dto.Ingredient;
import com.fx.repository.IngredienRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component //class that convert from form ID to Ingredients objects
@AllArgsConstructor
public class IngredentByIdConverter implements Converter<String, Ingredient> {

    private IngredienRepository ingredienRepository;

    @Override
    public Ingredient convert(String source) {
        for (var t : ingredienRepository.findAll()) {
            if (source.equals(t.getId())) {
                return t;
            }
        }
        return null;
    }
}
