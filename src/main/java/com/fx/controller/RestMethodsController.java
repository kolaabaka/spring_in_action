package com.fx.controller;

import com.fx.dto.jpa.Ingredient;
import com.fx.repository.jpa.IngredienRepository;
import com.fx.repository.jpa.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "/rest", produces = "application/json")
@RequiredArgsConstructor
public class RestMethodsController {

    private final OrderRepository orderRepository;
    private final IngredienRepository ingredienRepository;

    @DeleteMapping("/order/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @GetMapping(value = "/ingredient/{ingredientId}")
    public ResponseEntity<Ingredient> ingredientById(@PathVariable("ingredientId") String ingredientId) {
        var ingredient = ingredienRepository.findById(ingredientId);
        if(ingredient.isPresent()) {
            return new ResponseEntity(ingredient.get(), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
