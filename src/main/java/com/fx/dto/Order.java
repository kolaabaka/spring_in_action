package com.fx.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order {

    //can use regex and even @CreditCardNumber(!!!) and more more more
    @NotBlank(message = "Delivery name cannot be empty")
    private String deliveryName;
    @NotBlank(message = "Delivery address cannot be empty")
    private String deliveryAddress;
    @NotBlank(message = "CVV name cannot be empty")
    private String ccCVV;
    @Size(min = 1, message = "What happend, where street food?")
    private List<StreetFood> streetFoodList = new ArrayList<>();

    public void addStreetFood(StreetFood streetFood) {
        this.streetFoodList.add(streetFood);
    }
}
