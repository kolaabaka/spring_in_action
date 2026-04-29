package com.fx.controller;

import com.fx.dto.jpa.Ingredient;
import com.fx.dto.jpa.Order;
import com.fx.dto.jpa.StreetFood;
import com.fx.repository.jpa.IngredienRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@SessionAttributes({"order"})
@RequestMapping("/design")
@AllArgsConstructor
public class DesignController {

    private final IngredienRepository ingredienRepository;

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
        List<Ingredient> allIngredients = new ArrayList<>();
        ingredienRepository.findAll().forEach(allIngredients::add);

        allIngredients.stream()
            .collect(Collectors.groupingBy(Ingredient::getIngredient))
            .forEach((type, list) ->
                model.addAttribute(type.toString().toLowerCase(), list)
            );
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
