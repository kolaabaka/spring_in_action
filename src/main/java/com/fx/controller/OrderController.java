package com.fx.controller;

import com.fx.dto.Order;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

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
    public String orderFormProcess(@ModelAttribute @Valid Order order, Errors errors, SessionStatus sessionStatus) { //Аfter EACH validation need errors object...
        if (errors.hasErrors()) {
            log.warn("Try not valid data in ORDER {}", errors);
            return "orderForm";
        }
        sessionStatus.setComplete();
        log.info(order.toString());
        return "redirect:/";
    }
}
