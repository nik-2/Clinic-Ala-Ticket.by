package by.puesosi.clinic.service;

import by.puesosi.clinic.constant.Constant;
import by.puesosi.clinic.dao.ClientRepository;
import by.puesosi.clinic.dao.DayRepository;
import by.puesosi.clinic.dao.TicketRepository;
import by.puesosi.clinic.entity.*;
import by.puesosi.clinic.util.CodeGenerator;
import by.puesosi.clinic.util.DifferenceBetweenServiceTimeAndNowGetter;
import by.puesosi.clinic.dao.ReestablishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.*;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    private ClientRepository clientRepository;
    private ReestablishRepository reestablishRepository;
    private DayRepository dayRepository;
    private TicketRepository ticketRepository;
    private JavaMailSender mailSender;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ServiceImpl(JavaMailSender mailSender, ClientRepository clientRepository,
                       ReestablishRepository reestablishRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       DayRepository dayRepository,
                       TicketRepository ticketRepository) {
        this.clientRepository = clientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.reestablishRepository = reestablishRepository;
        this.mailSender = mailSender;
        this.dayRepository = dayRepository;
        this.ticketRepository = ticketRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(email);
        if(client == null){
            throw new UsernameNotFoundException("Client not found");
        }
        return client;
    }

    @Override
    public void changePassword(String email, String password) {
        Client client = clientRepository.findByEmail(email);
        client.setPassword(bCryptPasswordEncoder.encode(password));
        clientRepository.save(client);
    }

    @Override
    public Client findUserByID(int id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.orElse(new Client());
    }

    @Override
    public List<Client> allClients() {
        return clientRepository.findAll();
    }

    @Override
    public boolean addReestablishCode(String email) throws MessagingException {
        Client client = clientRepository.findByEmail(email);
        if(client == null){
            return false;
        }
        else{
            if(client.getCode() == null) {
                String code = CodeGenerator.generateString();
                client.setCode(saveCode(code));
                clientRepository.save(client);
                sendEmail(email, code);
            }
            return true;
        }
    }

    @Override
    public boolean saveClient(Client client) {
        Client client1 = clientRepository.findByEmail(client.getEmail());

        if(client1 != null){
            return false;
        }

        client.setRoles(Collections.singleton(new Role(1, "ROLE_CLIENT")));
        client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
        return true;
    }

    @Override
    public ReestablishData saveCode(String code) {
        ReestablishData data = new ReestablishData(code);
        reestablishRepository.save(data);
        return data;
    }

    @Override
    public boolean deleteUser(int id) {
        if(clientRepository.findById(id).isPresent()){
            clientRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean checkCode(String email, String code) {
        Client client = clientRepository.findByEmail(email);
        ReestablishData reestablishData = client.getCode();
        if(reestablishData == null){
            return false;
        }
        return code.equals(client.getCode().getCode());
    }

    @Override
    public void sendEmail(String email, String code) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setText(Constant.EMAIL_TEXT + code);
        messageHelper.setSubject(Constant.EMAIL_SUBJECT);
        messageHelper.setTo(email);
        mailSender.send(mimeMessage);
    }

    @Override
    public void deleteReestablishCode() {
        List<Client> clients = clientRepository.findAll();
        for(Client client: clients){
            ReestablishData reestablishData = client.getCode();
            if(reestablishData != null){
                Date reestablishDate = reestablishData.getDate();
                Date currentDate =  new Date(System.currentTimeMillis() - 5*60*1000);
                if (reestablishDate.compareTo(currentDate) <= 0) {
                    client.setCode(null);
                    clientRepository.save(client);
                    reestablishRepository.delete(reestablishData);
                }
            }
        }
    }

    @Override
    public void addNextDay() {
        List<Day> days = dayRepository.findAll();
        Day firstDay = days.get(0);
        dayRepository.delete(firstDay);
        firstDay.setDate(LocalDate.now().plusDays(29));
        dayRepository.save(firstDay);
    }

    @Override
    public void clearUntimedTicket() {
        LocalDate currentLocalDate = LocalDate.now();
        List<Ticket> ticketsFromCurrentDay = ticketRepository.findAllByDayDate(currentLocalDate);
        for (Ticket ticket : ticketsFromCurrentDay){
            if(DifferenceBetweenServiceTimeAndNowGetter.getDifferenceBetweenEndServiceTimeAndNow(ticket.getTime().getTime()) > 30){
                ticketRepository.delete(ticket);
            }
        }

    }

}
