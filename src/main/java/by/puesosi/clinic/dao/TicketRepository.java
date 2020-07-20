package by.puesosi.clinic.dao;

import by.puesosi.clinic.entity.Day;
import by.puesosi.clinic.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAllByDoctorIdAndDay(int doctorId, Day day);

    List<Ticket> findAllByDayDate(LocalDate localDate);

    List<Ticket> findAllByClientEmail(String email);

    List<Ticket> findAllByDayAndDoctorEmail(Day day, String email);

    List<Ticket> findAllByDayIdAndDoctorEmailOrderByTimeAsc(int dayId, String email);

    Ticket findByDayIdAndDoctorIdAndClientEmail(int dayId, int doctorId, String email);
}
