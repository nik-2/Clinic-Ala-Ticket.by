package by.puesosi.clinic.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "service_time")
public class ServiceTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "time")
    private String time;

    @Transient
    @OneToOne(mappedBy = "time")
    private Ticket ticket;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "serviceTimes")
    private Set<Day> days;

    public ServiceTime(){}

    public ServiceTime(String time){
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Set<Day> getDays() {
        return days;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return getTime();
    }
}
