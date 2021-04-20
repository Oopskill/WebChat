package org.example.controllers;

import org.example.dao.PersonDAO;
import org.example.models.Message;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {
    private final PersonDAO personDAO;

    @Autowired
    public ChatController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping("/chat")
    public String chatPage(){
        return "/chat";
    }

    @GetMapping("/chat/show/{id}")
    public String index(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.personGetId(id));
        model.addAttribute("messages",personDAO.messagesList());
        model.addAttribute("message",new Message());
        return "/index";
    }

    @PostMapping("/chat/show/{id}")
    public String writeMessage(@PathVariable("id") int id, @ModelAttribute("message") Message message){
        personDAO.addMessage(message.getContent(),personDAO.personGetId(id));
        return "redirect:/chat/show/{id}";
    }

    @GetMapping("chat/newPerson")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "/newPerson";
    }

    @PostMapping("/newPerson")
    public String create(@ModelAttribute("person") Person person){
        personDAO.addPerson(person);
        return "redirect:/chat";
    }

    @GetMapping("/chat/show")
    public String show(Model model){
        model.addAttribute("people" ,personDAO.getPeople());
        return "/show";
    }

    @DeleteMapping("chat/show/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.deletePerson(id);
        return "redirect:/chat/show";
    }


}
