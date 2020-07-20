package by.puesosi.clinic.service.doctor;

import by.puesosi.clinic.dao.DayRepository;
import by.puesosi.clinic.dao.TicketRepository;
import by.puesosi.clinic.entity.Day;
import by.puesosi.clinic.entity.Ticket;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private DayRepository dayRepository;
    private TicketRepository ticketRepository;

    public DoctorServiceImpl(DayRepository dayRepository,
                             TicketRepository ticketRepository){
        this.dayRepository = dayRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public HashMap<Day, List<Ticket>> getDoctorTickets(Authentication authentication) {
        HashMap<Day, List<Ticket>> dayListHashMap = new HashMap<>();
        List<Day> days = dayRepository.findAll();
        for (Day day : days){
            List<Ticket> tickets = ticketRepository.findAllByDayAndDoctorEmail(day, authentication.getName());
            dayListHashMap.put(day, tickets);
        }
        return dayListHashMap;
    }

    @Override
    public List<Ticket> getDayDoctorTickets(Authentication authentication, int dayId) {
        return ticketRepository.findAllByDayIdAndDoctorEmailOrderByTimeAsc(dayId, authentication.getName());
    }
}
