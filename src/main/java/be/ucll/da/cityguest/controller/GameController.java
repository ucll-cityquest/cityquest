package be.ucll.da.cityguest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @RequestMapping("/greeting")
    public String greeting(String name) {
        return new String("hi");
    }
}
