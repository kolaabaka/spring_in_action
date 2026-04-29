package com.fx.dto.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //can use regex and even @CreditCardNumber(!!!) and more more more
    @NotBlank(message = "Delivery name cannot be empty")
    private String deliveryName;
    @NotBlank(message = "Delivery address cannot be empty")
    private String deliveryAddress;
    @NotBlank(message = "CVV name cannot be empty")
    private String ccCVV;
    @Size(min = 1, message = "What happend, where street food?")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<StreetFood> streetFoodList = new ArrayList<>();

    public void addStreetFood(StreetFood streetFood) {
        this.streetFoodList.add(streetFood);
    }
}
