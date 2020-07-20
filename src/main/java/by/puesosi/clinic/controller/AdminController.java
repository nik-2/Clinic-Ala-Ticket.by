package by.puesosi.clinic.controller;

import by.puesosi.clinic.entity.Doctor;
import by.puesosi.clinic.service.admin.AdminService;
import by.puesosi.clinic.service.client.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ClientService service;
    private AdminService adminService;

    public AdminController(ClientService service, AdminService adminService){
        this.service = service;
        this.adminService = adminService;
    }

    @GetMapping("chooseSpeciality")
    public String showSpeciality(Model model){
        model.addAttribute("specialities", service.allSpecialities());
        return "/admin/speciality-list";
    }

    @GetMapping("showClients")
    public String showClients(Model model){
        model.addAttribute("clients", adminService.allClients());
        return "/admin/clients-list";
    }

    @GetMapping("showDoctors")
    public String showDoctors(Model model){
        model.addAttribute("doctors", adminService.allDoctors());
        return "/admin/doctors-list";
    }

    @GetMapping("showAdmins")
    public String showAdmins(Model model){
        model.addAttribute("admins", adminService.allAdmins());
        return "/admin/admins-list";
    }

    @GetMapping("registration")
    public String showRegistrationPage(Model model){
        model.addAttribute("doctor", new Doctor());
        return "/admin/doctor-registration";
    }

    @PostMapping("addDoctor")
    public String addNewDoctor(@RequestParam("specialityId") int id,
                               @ModelAttribute("doctor") @Valid Doctor doctor,
                               BindingResult bindingResult,
                               HttpServletRequest servletRequest,
                               Model model,
                               RedirectAttributes redirectAttributes){
        String registrationResult = adminService.saveDoctor(doctor, id);
        if(bindingResult.hasErrors()){
            servletRequest.setAttribute("specialityId", id);
            return "/admin/doctor-registration";
        }
        if(registrationResult.equals("email")){
            model.addAttribute("emailError", "Client with this email already exist");
            servletRequest.setAttribute("specialityId", id);
            return "/admin/doctor-registration";
        }
        if(registrationResult.equals("speciality")){
            model.addAttribute("speciality", "Wrong doctor's speciality. Check it!");
            servletRequest.setAttribute("specialityId", id);
            return "/admin/doctor-registration";
        }
        redirectAttributes.addFlashAttribute("success", "Doctor was successfully added.");
        return "redirect:/clinic/profile";
    }
}
