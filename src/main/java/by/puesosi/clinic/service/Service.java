package by.puesosi.clinic.service;

import by.puesosi.clinic.entity.Client;
import by.puesosi.clinic.entity.ReestablishData;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.mail.MessagingException;
import java.util.List;

public interface Service extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    void changePassword(String email, String password);

    Client findUserByID(int id);

    List<Client> allClients();

    boolean addReestablishCode(String email) throws MessagingException;

    boolean saveClient(Client client);

    ReestablishData saveCode(String code);

    boolean deleteUser(int id);

    boolean checkCode(String email, String code);

    void sendEmail(String email, String code) throws MessagingException;

    void deleteReestablishCode();

    void addNextDay();

    void clearUntimedTicket();
}
