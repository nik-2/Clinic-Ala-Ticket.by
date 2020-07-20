package by.puesosi.clinic.service.admin;

import by.puesosi.clinic.entity.Admin;
import by.puesosi.clinic.entity.Client;
import by.puesosi.clinic.entity.Doctor;

import java.util.List;

public interface AdminService {
    String saveDoctor(Doctor doctor, int specialityId);

    List<Client> allClients();

    List<Doctor> allDoctors();

    List<Admin> allAdmins();
}
