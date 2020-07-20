package by.puesosi.clinic.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Doctor extends Client {

    @Transient
    @OneToOne(mappedBy = "doctor")
    private Ticket ticket;

    public Doctor() {
        super();
    }

    public Doctor(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    @Override
    public Speciality getSpeciality() {
        return super.getSpeciality();
    }

    @Override
    public void setSpeciality(Speciality speciality) {
        super.setSpeciality(speciality);
    }

    @Override
    public ReestablishData getCode() {
        return super.getCode();
    }

    @Override
    public void setCode(ReestablishData code) {
        super.setCode(code);
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public Set<Role> getRoles() {
        return super.getRoles();
    }

    @Override
    public void setRoles(Set<Role> roles) {
        super.setRoles(roles);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public Ticket getTicket() {
        return ticket;
    }

    @Override
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
