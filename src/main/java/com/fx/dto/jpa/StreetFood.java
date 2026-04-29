package com.fx.dto.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "street_food")
public class StreetFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "At least one ingredient")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "street_food_ingredient",  // имя промежуточной таблицы
        joinColumns = @JoinColumn(name = "street_food_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredient;
}
