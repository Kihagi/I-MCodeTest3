package com.mathenge.swapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author Mathenge on 4/14/2020
 */
@Controller
public class PageController {

    @GetMapping(value = {"/", "/home"})
    public String showDefaultPage() {
        return "home";
    }

    @GetMapping("/details")
    public String showCharacterDetailsPage(@Valid @NotBlank @RequestParam String id, Model model) {
        model.addAttribute("characterId", id);
        return "character-details";
    }
}
