package com.fx.repository.jpa;

import com.fx.dto.jpa.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredienRepository extends CrudRepository<Ingredient, Long> {
}
