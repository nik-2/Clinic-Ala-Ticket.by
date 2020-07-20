package by.puesosi.clinic.controller;

import by.puesosi.clinic.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;

@Controller
@RequestMapping("/send")
public class ReestablishController {

    private Service service;

    @Autowired
    public ReestablishController(Service service) {
        this.service = service;
    }

    @GetMapping("/view")
    public String send(Model model){
        String successemail = (String)model.asMap().getOrDefault("successemail", "false");
        String successcode = (String)model.asMap().getOrDefault("successcode", "false");
        String email = (String)model.asMap().getOrDefault("email", "false");
        model.addAttribute("successemail", successemail);
        model.addAttribute("successcode", successcode);
        model.addAttribute("email", email);
        return "/guest/forgot";
    }

    @PostMapping("/code")
    public String send(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        boolean flag;
        try {
            flag = service.addReestablishCode(email);
            if(flag) {
                redirectAttributes.addFlashAttribute("successemail", "true");
                redirectAttributes.addFlashAttribute("email", email);
            }
            else{
                redirectAttributes.addAttribute("error","true");
            }
            return "redirect:/send/view";
        } catch (MessagingException e) {
            redirectAttributes.addAttribute("error","true");
            return "redirect:/send/view";
        }
    }

    @PostMapping("/check")
    public String send(@RequestParam("code") String code, @RequestParam("email") String email,
                       RedirectAttributes redirectAttributes) {
        if (service.checkCode(email, code)){
            redirectAttributes.addFlashAttribute("successemail", "true");
            redirectAttributes.addFlashAttribute("successcode", "true");
        }
        else {
            redirectAttributes.addFlashAttribute("successemail", "true");
            redirectAttributes.addAttribute("error","true");
        }
        redirectAttributes.addFlashAttribute("email", email);
        return "redirect:/send/view";
    }

    @PostMapping("/change")
    public String changePassword(@RequestParam("password") String password, @RequestParam("email") String email,
                       RedirectAttributes redirectAttributes) {
            service.changePassword(email, password);
            redirectAttributes.addAttribute("changePassword","success");
            return "redirect:/login";
    }
}
