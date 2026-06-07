package com.fx.controller;

import com.fx.dto.jpa.StreetFood;
import com.fx.integration.configuration.IntegrationConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/integration")
public class IntegrationController {

    private final IntegrationConfiguration.GatewaySoutIntegration gatewaySoutIntegration;

    @PostMapping("/street_food")
    public void gateStreetFoodController(@RequestBody List<StreetFood> streetFoodList){
        gatewaySoutIntegration.soutGate(streetFoodList);
    }

}
