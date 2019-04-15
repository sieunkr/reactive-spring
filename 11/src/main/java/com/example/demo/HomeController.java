package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@Controller
public class HomeController {

    @GetMapping("/")
        public String index(Model model){
            model.addAttribute("coffees", Flux.just(
                    new Coffee("1", "mocha"),
                    new Coffee("2", "latte"),
                    new Coffee("3", "americano")
            ));

            return "index";
    }

}
