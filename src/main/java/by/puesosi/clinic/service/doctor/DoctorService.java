package by.puesosi.clinic.service.doctor;

import by.puesosi.clinic.entity.Day;
import by.puesosi.clinic.entity.Ticket;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.List;

public interface DoctorService {
    HashMap<Day, List<Ticket>> getDoctorTickets(Authentication authentication);

    List<Ticket> getDayDoctorTickets(Authentication authentication, int dayId);
}
