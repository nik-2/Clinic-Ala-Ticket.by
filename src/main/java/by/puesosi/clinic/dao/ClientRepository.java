package by.puesosi.clinic.dao;

import by.puesosi.clinic.entity.Client;
import by.puesosi.clinic.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Integer> {
    Client findByEmail(String email);

    List<Client> findAllBySpeciality(Speciality speciality);

    List<Client> findAllByOrderByLastName();
}
