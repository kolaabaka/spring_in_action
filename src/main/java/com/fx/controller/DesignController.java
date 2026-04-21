package com.fx.controller;

import com.fx.dto.Ingredients;
import com.fx.dto.Ingridient;
import com.fx.dto.Order;
import com.fx.dto.StreetFood;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@SessionAttributes("StreetFood")
@RequestMapping("design")
public class DesignController {

    @GetMapping
    public String design() {
        return "design";
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
        List<Ingridient> ingredientList = Arrays.asList(
            new Ingridient("CHC", "chicken", Ingredients.CHICKEN),
            new Ingridient("CHF", "fried chicken", Ingredients.CHICKEN),

            new Ingridient("PIT", "pita", Ingredients.PITA),
            new Ingridient("PIC", "corn pita", Ingredients.PITA),

            new Ingridient("CUC", "cucumber", Ingredients.VEGETABLE),
            new Ingridient("TOM", "tomato", Ingredients.VEGETABLE),

            new Ingridient("CHE", "cheese", Ingredients.CHEESE),

            new Ingridient("SAU", "sauce", Ingredients.SAUCE),
            new Ingridient("SAH", "hot sauce", Ingredients.SAUCE)
        );

        Arrays.stream(Ingredients.values())
            .forEach(val -> {
                List<Ingridient> filtered = ingredientList.stream()
                    .filter(ing -> ing.getIngredient().equals(val))
                    .collect(Collectors.toList());  // Важно!
                model.addAttribute(val.toString().toLowerCase(), filtered);
            });
    }
}
