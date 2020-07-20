package by.puesosi.clinic.service.client;

import by.puesosi.clinic.entity.*;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.List;

public interface ClientService {
    List<Speciality> allSpecialities();

    List<Client> doctorsWithIdSpec(int id);

    Speciality specialityWithId(int id);

    List<Day> allDays();

    List<Day> getFirstWeek(List<Day> days);

    List<Day> getSecondWeek(List<Day> days);

    List<Day> getThirdWeek(List<Day> days);

    List<Day> getFourthWeek(List<Day> days);

    List<Day> getFifthWeek(List<Day> days);

    HashMap<Day, List<ServiceTime>> getFreeServiceTime(int doctorId);

    Ticket orderTicket(int doctorId, int dayId, int serviceTimeId, Authentication authentication);

    Ticket getLastClientTicket(Authentication authentication);

    String checkTicketDay(int dayId, int doctorId, Authentication authentication);

}
