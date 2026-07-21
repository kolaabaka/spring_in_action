package com.fx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test_page_button") //even static resources
    public String testPageButton(){
        return "testPage";
    }

    @GetMapping("/test_page_button_async") //even static resources
    public Mono<String> testPageButtonWithMono(){
        return Mono.just("testPage");
    }
}
