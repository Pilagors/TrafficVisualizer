package fr.monfort.backend.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/traffic/visualizer/v1")
public class TrafficVisualizerController {
    
    @GetMapping
    public String home() {
        return "Welcome Home";
    }
}
