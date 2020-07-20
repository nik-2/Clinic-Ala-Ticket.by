package by.puesosi.clinic.dao;

import by.puesosi.clinic.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByEmail(String email);

    List<Doctor> findAllByOrderByLastName();
}
