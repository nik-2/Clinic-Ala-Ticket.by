package by.puesosi.clinic.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("/clinic")
public class Controller {

    @GetMapping("/main")
    public String viewMain(){
        return "main";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model){
        String successText = (String)model.asMap().getOrDefault("success", "nothing");
        if(!successText.equals("nothing")) {
            model.addAttribute("success", successText);
        }
        return "profile";
    }

    @GetMapping("/error")
    public String viewError(){
        return "error/access-denied";
    }
}
