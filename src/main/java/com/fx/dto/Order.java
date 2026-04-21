package com.fx.dto;

import lombok.Data;

import java.util.List;

@Data
public class Order {

    private String deliveryName;
    private String deliveryAddress;
    private String ccCVV;
    private List<StreetFood> streetFoodList;

    public void addStreetFood(StreetFood streetFood){
        this.streetFoodList.add(streetFood);
    }
}
