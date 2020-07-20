package by.puesosi.clinic.controller;

import by.puesosi.clinic.entity.Client;
import by.puesosi.clinic.entity.Day;
import by.puesosi.clinic.entity.Speciality;
import by.puesosi.clinic.entity.Ticket;
import by.puesosi.clinic.service.client.ClientService;
import by.puesosi.clinic.util.DayOfWeekGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Month;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping("/day")
    public String viewDays(@RequestParam("id") int id, Model model){
        List<Day> days = clientService.allDays();
        List<Day> fifthWeekDays = clientService.getFifthWeek(days);
        model.addAttribute("start", DayOfWeekGetter.getDayOfWeek(days.get(0).getDate()));
        model.addAttribute("end", 6 - DayOfWeekGetter.getDayOfWeek(fifthWeekDays.get(fifthWeekDays.size() - 1).getDate()));
        Month firstMonth = days.get(0).getDate().getMonth();
        Month secondMonth = days.get(days.size() - 1).getDate().getMonth();
        if(!firstMonth.equals(secondMonth)){
            model.addAttribute("month", firstMonth + "/" + secondMonth);
        }
        else {
            model.addAttribute("month", firstMonth);
    }
        model.addAttribute("days1", clientService.getFirstWeek(days));
        model.addAttribute("days2", clientService.getSecondWeek(days));
        model.addAttribute("days3", clientService.getThirdWeek(days));
        model.addAttribute("days4", clientService.getFourthWeek(days));
        model.addAttribute("days5", fifthWeekDays);

        model.addAttribute("freeServiceTimes", clientService.getFreeServiceTime(id));
       return "/client/access_days";
    }

    @GetMapping("/success")
    public String viewSuccessPage(Model model,
                                  Authentication authentication){
        Ticket ticket = (Ticket)model.asMap().getOrDefault("ticket", clientService.getLastClientTicket(authentication));
        String check = (String)model.asMap().getOrDefault("check", "0");
        model.addAttribute("ticket", ticket);
        model.addAttribute("check", check);
        if(ticket != null && check.equals("0")){
            return "/client/success";
        }
        else {
            return "/client/fail";
        }
    }

    @GetMapping("/ticket")
    public String addTicket(@RequestParam("doctorId") int doctorId,
                            @RequestParam("dayId") int dayId,
                            @RequestParam("serviceTimeId") int serviceTimeId,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes){
        String check = clientService.checkTicketDay(dayId, doctorId, authentication);
        if(check.equals("0")) {
            redirectAttributes.addFlashAttribute("ticket", clientService.orderTicket(doctorId, dayId, serviceTimeId, authentication));
        }
        else{
            redirectAttributes.addFlashAttribute("check", check);
        }
        return "redirect:/client/success";
    }


    @GetMapping("/doctors")
    public String viewDoctors(@RequestParam("id") int id, Model model){
        List<Client> doctors = clientService.doctorsWithIdSpec(id);
        model.addAttribute("doctors", doctors);
        return "/client/doctors-list";
    }

    @GetMapping("/order")
    public String viewSpeciality(Model model){
        List<Speciality> specialities = clientService.allSpecialities();
        model.addAttribute("specialities", specialities);
        return "/client/speciality-list";
    }

}
