package com.fx.controller;

import com.fx.dto.Ingredient;
import com.fx.dto.Ingredients;
import com.fx.dto.Order;
import com.fx.dto.StreetFood;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@SessionAttributes({"order"})
@RequestMapping("/design")
public class DesignController {

    @GetMapping
    public String design() {
        return "designForm";
    }

    @ModelAttribute("order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute("streetFood")
    public StreetFood streetFood() {
        return new StreetFood();
    }

    @ModelAttribute
    public void addIngridientListToModel(Model model) {
        List<Ingredient> ingredientList = Arrays.asList(
            new Ingredient("CHC", "chicken", Ingredients.CHICKEN),
            new Ingredient("CHF", "fried chicken", Ingredients.CHICKEN),

            new Ingredient("PIT", "pita", Ingredients.PITA),
            new Ingredient("PIC", "corn pita", Ingredients.PITA),

            new Ingredient("CUC", "cucumber", Ingredients.VEGETABLE),
            new Ingredient("TOM", "tomato", Ingredients.VEGETABLE),

            new Ingredient("CHE", "cheese", Ingredients.CHEESE),

            new Ingredient("SAU", "sauce", Ingredients.SAUCE),
            new Ingredient("SAH", "hot sauce", Ingredients.SAUCE)
        );

        //Amazing non repository mock...
        Arrays.stream(Ingredients.values())
            .forEach(val -> {
                List<Ingredient> filtered = ingredientList.stream()
                    .filter(ing -> ing.getIngredient().equals(val))
                    .collect(Collectors.toList());
                model.addAttribute(val.toString().toLowerCase(), filtered);
            });
    }

    @PostMapping
    public String processStreetFood(@Valid StreetFood streetFood, Errors errors, Order order) { //Аfter EACH validation need errors object...
        if (errors.hasErrors()) {
            log.warn("Try not valid data in DESIGN {}", errors);
            return "designForm";
        }
        order.addStreetFood(streetFood);

        log.info("Processing street food: {}", streetFood);

        return "redirect:/orders/current";
    }
}
