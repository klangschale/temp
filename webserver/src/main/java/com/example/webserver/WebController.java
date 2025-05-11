package com.example.webserver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class WebController {

    private final WebService webService;

    public WebController(WebService webService) {
        this.webService = webService;
    }

    @GetMapping
    public String showForm() {
        return "index";
    }

    @PostMapping("/submit")
    public String handleForm(@RequestParam String text,
                             @RequestParam String action,
                             Model model) {
        String result = webService.process(text, action);
        model.addAttribute("originalText", text);
        model.addAttribute("action", action);
        model.addAttribute("resultText", result);
        return "result";
    }
}
