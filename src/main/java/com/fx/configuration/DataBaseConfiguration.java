package com.fx.configuration;

import com.fx.dto.jpa.Ingredient;
import com.fx.dto.jpa.Ingredients;
import com.fx.repository.jpa.IngredienRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseConfiguration {

    @Bean //like post construct but for application context, work after all beans configurations
    public ApplicationRunner dataLoader(IngredienRepository ingredienRepository) {
        return args -> {
            ingredienRepository.save(new Ingredient("CHC", "chicken", Ingredients.CHICKEN));
            ingredienRepository.save(new Ingredient("CHF", "fried chicken", Ingredients.CHICKEN));
            ingredienRepository.save(new Ingredient("PIT", "pita", Ingredients.PITA));
            ingredienRepository.save(new Ingredient("PIC", "corn pita", Ingredients.PITA));
            ingredienRepository.save(new Ingredient("CUC", "cucumber", Ingredients.VEGETABLE));
            ingredienRepository.save(new Ingredient("TOM", "tomato", Ingredients.VEGETABLE));
            ingredienRepository.save(new Ingredient("CHE", "cheese", Ingredients.CHEESE));
            ingredienRepository.save(new Ingredient("SAU", "sauce", Ingredients.SAUCE));
            ingredienRepository.save(new Ingredient("SAH", "hot sauce", Ingredients.SAUCE));
        };
    }
}
