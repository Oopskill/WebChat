package org.example.controllers;

import org.example.dao.PersonDAO;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//it need to realise in future(configure is setup, just need to realise it)
@Controller
public class LoginController {
    private final PersonDAO personDAO;

    @Autowired
    public LoginController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("person")Person person, Model model){
        model.addAttribute("person",person);
        return "/login";
    }

    @PostMapping("/login")
    public String enterData(@ModelAttribute("person") Person person){
        if (personDAO.getPeople().stream()
                        .filter(p -> p.getName().equals(person.getName()) && p.getPassword()
                        .equals(person.getPassword())).findAny().orElse(null) != null) {
            return "redirect:/chat/show/{id}";

        }
        return "/login";
    }
}
