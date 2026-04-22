package com.fx.converter;

import com.fx.dto.Ingredient;
import com.fx.dto.Ingredients;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component //class that convert from form ID to Ingredients objects
public class IngredentByIdConverter implements Converter<String, Ingredient> {
    Map<String, Ingredient> stringIngredientsMap = new HashMap<>();

    public IngredentByIdConverter() {
        stringIngredientsMap.put("CHC", new Ingredient("CHF", "fried chicken", Ingredients.CHICKEN));
        stringIngredientsMap.put("CHF", new Ingredient("CHF", "fried chicken", Ingredients.CHICKEN));
        stringIngredientsMap.put("PIT", new Ingredient("PIT", "pita", Ingredients.PITA));
        stringIngredientsMap.put("PIC", new Ingredient("PIC", "corn pita", Ingredients.PITA));
        stringIngredientsMap.put("CUC", new Ingredient("CUC", "cucumber", Ingredients.VEGETABLE));
        stringIngredientsMap.put("TOM", new Ingredient("TOM", "tomato", Ingredients.VEGETABLE));
        stringIngredientsMap.put("CHE", new Ingredient("CHE", "cheese", Ingredients.CHEESE));
        stringIngredientsMap.put("SAU", new Ingredient("SAU", "sauce", Ingredients.SAUCE));
        stringIngredientsMap.put("SAH", new Ingredient("SAH", "hot sauce", Ingredients.SAUCE));
    }

    @Override
    public Ingredient convert(String source) {
        return stringIngredientsMap.get(source);
    }
}
