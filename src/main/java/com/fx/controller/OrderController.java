package com.fx.controller;

import com.fx.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/orders")
@SessionAttributes({"order"})
public class OrderController {

    @GetMapping("/current")
    public String orderForm(@ModelAttribute Order order) {
        log.info(String.valueOf(order));
        return "orderForm";
    }

    @PostMapping
    public String orderFormProcess(@ModelAttribute Order order){
        log.info(order.toString());
        return "redirect:/";
    }
}
