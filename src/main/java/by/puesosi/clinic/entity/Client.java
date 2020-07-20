package by.puesosi.clinic.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "client")
public class Client implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Please, provide first name")
    @Pattern(regexp = "[A-Z][a-z]+(-[A-Z][a-z]+)?", message = "Please, input correct first name, for example: Username or Username-Username")
    @Size(min = 2, max = 25, message = "Incorrect first name. Use more then 1 char and less then 26")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Please, provide first name")
    @Pattern(regexp = "[A-Z][a-z]+(-[A-Z][a-z]+)?", message = "Please, input correct first name, for example: Username or Username-Username")
    @Size(min = 2, max = 25, message = "Incorrect first name. Use more then 1 char and less then 26")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Please, input correct email, for example: abc@xxx.xx")
    @NotEmpty(message = "Please, provide email")
    @Size(min = 5, max = 30, message = "Incorrect email. Please, use more then 4 chars and less 31")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Please, provide password")
    @Pattern(regexp = "[A-Za-z_\\d]{4,15}", message = "Use only numbers, letters and _")
    @Size(min = 4, max = 15, message = "Incorrect password. Please, use more then 3 chars and less then 16")
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToOne
    @JoinColumn(name = "reestablish_data")
    private ReestablishData code;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

    @Transient
    @OneToOne(mappedBy = "client")
    private Ticket ticket;

    public Client() {
    }

    public Client(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public ReestablishData getCode() {
        return code;
    }

    public void setCode(ReestablishData code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", roles=").append(roles);
        sb.append(", code=").append(code);
        sb.append('}');
        return sb.toString();
    }
}
