package by.puesosi.clinic.controller;

import by.puesosi.clinic.entity.Client;
import by.puesosi.clinic.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private Service service;

    @Autowired
    public RegistrationController(Service service) {
        this.service = service;
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("client", new Client());
        return "guest/register";
    }

    @PostMapping("/registration")
    public String saveClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "guest/register";
        }
        if(!service.saveClient(client)){
            model.addAttribute("emailError", "Client with this email already exist");
            return "guest/register";
        }

        return "redirect:/clinic/profile";
    }


}

