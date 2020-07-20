package by.puesosi.clinic.service.admin;

import by.puesosi.clinic.dao.AdminRepository;
import by.puesosi.clinic.dao.ClientRepository;
import by.puesosi.clinic.dao.DoctorRepository;
import by.puesosi.clinic.dao.SpecialityRepository;
import by.puesosi.clinic.entity.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private ClientRepository clientRepository;
    private DoctorRepository doctorRepository;
    private SpecialityRepository specialityRepository;
    private AdminRepository adminRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminServiceImpl(DoctorRepository doctorRepository,
                            SpecialityRepository specialityRepository,
                            ClientRepository clientRepository,
                            AdminRepository adminRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder){
        this.doctorRepository = doctorRepository;
        this.specialityRepository = specialityRepository;
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String saveDoctor(Doctor doctor, int specialityId) {
        Client client = clientRepository.findByEmail(doctor.getEmail());
        if(client != null){
            return "email";
        }
        else{
            Optional<Speciality> optionalSpeciality = specialityRepository.findById(specialityId);
            if(optionalSpeciality.isPresent()){
                doctor.setSpeciality(optionalSpeciality.get());
                doctor.setRoles(Collections.singleton(new Role(3, "ROLE_DOCTOR")));
                doctor.setPassword(bCryptPasswordEncoder.encode(doctor.getPassword()));
                doctorRepository.save(doctor);
            }
            else{
                return "speciality";
            }
        }
        return "true";
    }

    @Override
    public List<Client> allClients() {
        return clientRepository.findAllByOrderByLastName();
    }

    @Override
    public List<Doctor> allDoctors() {
        return doctorRepository.findAllByOrderByLastName();
    }

    @Override
    public List<Admin> allAdmins() {
        return adminRepository.findAllByOrderByLastName();
    }
}
