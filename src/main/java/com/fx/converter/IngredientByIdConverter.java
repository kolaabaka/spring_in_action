package com.fx.converter;

import com.fx.dto.jpa.Ingredient;
import com.fx.repository.jpa.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component //class that convert from form ID to Ingredients objects
@AllArgsConstructor
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepository ingredientRepository;

    @Override
    public Ingredient convert(String source) {
        for (var t : ingredientRepository.findAll()) {
            if (source.equals(t.getId())) {
                return t;
            }
        }
        return null;
    }
}
