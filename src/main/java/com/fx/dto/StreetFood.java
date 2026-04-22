package com.fx.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class StreetFood {
    @Size(min = 3, message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "At least one ingredient")
    private List<Ingredient> ingredient;
}
