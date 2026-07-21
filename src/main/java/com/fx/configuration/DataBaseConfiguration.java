package com.fx.configuration;

import com.fx.dto.jpa.Ingredient;
import com.fx.dto.jpa.Ingredients;
import com.fx.repository.jpa.IngredientRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.fx.repository.mongo")
public class DataBaseConfiguration {

    @Bean //like post construct but for application context, work after all beans configurations
    @Profile("dev") // or could use @Profile("!dev")
    public ApplicationRunner dataLoader(IngredientRepository ingredientRepository) {
        return args -> {
            ingredientRepository.save(new Ingredient("CHC", "chicken", Ingredients.CHICKEN));
            ingredientRepository.save(new Ingredient("CHF", "fried chicken", Ingredients.CHICKEN));
            ingredientRepository.save(new Ingredient("PIT", "pita", Ingredients.PITA));
            ingredientRepository.save(new Ingredient("PIC", "corn pita", Ingredients.PITA));
            ingredientRepository.save(new Ingredient("CUC", "cucumber", Ingredients.VEGETABLE));
            ingredientRepository.save(new Ingredient("TOM", "tomato", Ingredients.VEGETABLE));
            ingredientRepository.save(new Ingredient("CHE", "cheese", Ingredients.CHEESE));
            ingredientRepository.save(new Ingredient("SAU", "sauce", Ingredients.SAUCE));
            ingredientRepository.save(new Ingredient("SAH", "hot sauce", Ingredients.SAUCE));
        };
    }
}
