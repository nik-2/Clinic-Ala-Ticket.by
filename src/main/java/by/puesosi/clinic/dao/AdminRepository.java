package by.puesosi.clinic.dao;

import by.puesosi.clinic.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    List<Admin> findAllByOrderByLastName();
}
