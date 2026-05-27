package com.fx.controller;

import com.fx.dto.jpa.Order;
import com.fx.repository.jpa.OrderRepository;
import com.fx.service.kafka.KafkaProducerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@Slf4j
@RequestMapping("/orders")
@SessionAttributes({"order"})
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final KafkaProducerService kafkaProducerService;

    @GetMapping("/current")
    public String orderForm(@ModelAttribute Order order) {
        log.info(String.valueOf(order));
        return "orderForm";
    }

    @PostMapping
    public String orderFormProcess(@ModelAttribute Order order, Errors errors, SessionStatus sessionStatus) { //Аfter EACH validation need errors object...
        if (errors.hasErrors()) {
            log.warn("Try not valid data in ORDER {}", errors);
            return "orderForm";
        }
        kafkaProducerService.sendOrder(order);
        orderRepository.save(order);
        sessionStatus.setComplete();
        log.info(order.toString());
        return "redirect:/";
    }
}
