package com.fx.repository;

import com.fx.dto.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredienRepository extends CrudRepository<Ingredient, Long> {
}
