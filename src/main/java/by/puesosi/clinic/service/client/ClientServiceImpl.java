package by.puesosi.clinic.service.client;

import by.puesosi.clinic.dao.*;
import by.puesosi.clinic.entity.*;
import by.puesosi.clinic.util.DifferenceBetweenServiceTimeAndNowGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private SpecialityRepository specialityRepository;
    private ClientRepository clientRepository;
    private DayRepository dayRepository;
    private TicketRepository ticketRepository;
    private DoctorRepository doctorRepository;
    private ServiceTimeRepository serviceTimeRepository;

    @Autowired
    public ClientServiceImpl(SpecialityRepository specialityRepository,
                             ClientRepository clientRepository,
                             DayRepository dayRepository,
                             TicketRepository ticketRepository,
                             DoctorRepository doctorRepository,
                             ServiceTimeRepository serviceTimeRepository) {
        this.specialityRepository = specialityRepository;
        this.clientRepository = clientRepository;
        this.dayRepository = dayRepository;
        this.ticketRepository = ticketRepository;
        this.doctorRepository = doctorRepository;
        this.serviceTimeRepository = serviceTimeRepository;
    }

    @Override
    public List<Speciality> allSpecialities() {
        return specialityRepository.findAll();
    }


    @Override
    public List<Client> doctorsWithIdSpec(int id) {
        return clientRepository.findAllBySpeciality(specialityWithId(id));
    }

    @Override
    public Speciality specialityWithId(int id) {
        Optional<Speciality> optionalSpeciality = specialityRepository.findById(id);
        return optionalSpeciality.orElse(new Speciality());
    }

    @Override
    public List<Day> allDays() {
        return dayRepository.findAll();
    }

    @Override
    public List<Day> getFirstWeek(List<Day> days) {
        List<Day> firstWeek = new ArrayList<>();
        for(Day day : days){
            firstWeek.add(day);
            if(day.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                break;
            }
        }
        return firstWeek;
    }

    @Override
    public List<Day> getSecondWeek(List<Day> days) {
        int check = 0;
        List<Day> secondWeek = new ArrayList<>();
        for(Day day : days){
            if(check == 1) {
                secondWeek.add(day);
            }
            if(day.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                check++;
            }
            if(check == 2){
                break;
            }
        }
        return secondWeek;
    }

    @Override
    public List<Day> getThirdWeek(List<Day> days) {
        int check = 0;
        List<Day> thirdWeek = new ArrayList<>();
        for(Day day : days){
            if(check == 2) {
                thirdWeek.add(day);
            }
            if(day.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                check++;
            }
            if(check == 3){
                break;
            }
        }
        return thirdWeek;
    }

    @Override
    public List<Day> getFourthWeek(List<Day> days) {
        int check = 0;
        List<Day> fourthWeek = new ArrayList<>();
        for(Day day : days){
            if(check == 3) {
                fourthWeek.add(day);
            }
            if(day.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                check++;
            }
            if(check == 4){
                break;
            }
        }
        return fourthWeek;
    }

    @Override
    public List<Day> getFifthWeek(List<Day> days) {
        int check = 0;
        List<Day> fifthWeek = new ArrayList<>();
        for(Day day : days){
            if(check == 4) {
                fifthWeek.add(day);
            }
            if(day.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                check++;
            }
            if(check == 5){
                break;
            }
        }
        return fifthWeek;
    }

    @Override
    public HashMap<Day, List<ServiceTime>> getFreeServiceTime(int doctorId) {
        HashMap<Day, List<ServiceTime>> dayListHashMap = new HashMap<>();
        List<Day> days = dayRepository.findAll();
        for(Day day : days){
            List<ServiceTime> serviceTimes = day.getServiceTimes();
            List<Ticket> tickets = ticketRepository.findAllByDoctorIdAndDay(doctorId, day);
            if(tickets != null) {
                for (Ticket ticket : tickets) {
                    ServiceTime serviceTime = ticket.getTime();
                    if(serviceTime != null){
                        serviceTimes.remove(serviceTime);
                    }
                }
            }
            if(day.getDate().compareTo(LocalDate.now()) == 0){
                serviceTimes.removeIf(serviceTime -> DifferenceBetweenServiceTimeAndNowGetter.getDifferenceBetweenStartServiceTimeAndNow(serviceTime.getTime()) > -60);
            }
                dayListHashMap.put(day, serviceTimes);
        }
        return dayListHashMap;
    }

    @Override
    public Ticket orderTicket(int doctorId, int dayId, int serviceTimeId, Authentication authentication) {
        Ticket ticket = new Ticket();
        Doctor doctor = new Doctor();
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        if(optionalDoctor.isPresent()){
            doctor = optionalDoctor.get();
        }
        Day day = new Day();
        Optional<Day> optionalDay = dayRepository.findById(dayId);
        if (optionalDay.isPresent()) {
            day = optionalDay.get();
        }
        ServiceTime serviceTime = new ServiceTime();
        Optional<ServiceTime> optionalServiceTime = serviceTimeRepository.findById(serviceTimeId);
        if(optionalServiceTime.isPresent()){
            serviceTime = optionalServiceTime.get();
        }
        Client client = new Client();
        if(authentication != null){
             client = clientRepository.findByEmail(authentication.getName());
        }
        ticket.setDoctor(doctor);
        ticket.setClient(client);
        ticket.setDay(day);
        ticket.setTime(serviceTime);
        ticketRepository.save(ticket);
        return ticket;
    }

    @Override
    public Ticket getLastClientTicket(Authentication authentication) {
        List<Ticket> tickets = ticketRepository.findAllByClientEmail(authentication.getName());
        int size = tickets.size();
        if(size != 0){
            return tickets.get(size - 1);
        }
        else{
            return null;
        }
    }

    @Override
    public String checkTicketDay(int dayId, int doctorId, Authentication authentication) {
        Ticket ticket = ticketRepository.findByDayIdAndDoctorIdAndClientEmail(dayId, doctorId, authentication.getName());
        if(ticket != null){
            return "1";
        }
        else{
            return "0";
        }
    }

}
