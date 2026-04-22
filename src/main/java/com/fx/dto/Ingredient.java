package com.fx.dto;

import lombok.Data;

@Data
public class Ingredient {

    private final String id;
    private final String name;
    private final Ingredients ingredient;
}
