package by.puesosi.clinic.controller;

import by.puesosi.clinic.entity.Day;
import by.puesosi.clinic.service.client.ClientService;
import by.puesosi.clinic.service.doctor.DoctorService;
import by.puesosi.clinic.util.DayOfWeekGetter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Month;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private ClientService clientService;
    private DoctorService doctorService;

    public DoctorController(ClientService clientService,
                            DoctorService doctorService){
        this.clientService = clientService;
        this.doctorService = doctorService;
    }

    @GetMapping("/showDays")
    public String showAccessDays(Model model, Authentication authentication){
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

        model.addAttribute("tickets", doctorService.getDoctorTickets(authentication));
        return "/doctor/access_days";
    }

    @GetMapping("/tickets")
    public String showAllTicketsToDoctor(@RequestParam int dayId, Model model, Authentication authentication){
        model.addAttribute("tickets", doctorService.getDayDoctorTickets(authentication, dayId));
        return "/doctor/ticket-list";
    }


}
