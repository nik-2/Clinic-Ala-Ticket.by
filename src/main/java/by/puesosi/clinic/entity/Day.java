package by.puesosi.clinic.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "access_days")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private LocalDate date;

    @Transient
    @OneToOne(mappedBy = "day")
    private Ticket ticket;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ServiceTime> serviceTimes;

    public Day(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<ServiceTime> getServiceTimes() {
        return serviceTimes;
    }

    public void setServiceTimes(List<ServiceTime> serviceTimes) {
        this.serviceTimes = serviceTimes;
    }


    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Day{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", serviceTimes=").append(serviceTimes);
        sb.append('}');
        return sb.toString();
    }
}
