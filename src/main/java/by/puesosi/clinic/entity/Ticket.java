package by.puesosi.clinic.entity;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  int id;

    //One to one
    @OneToOne
    @JoinColumn(name = "day")
    private Day day;

    //One to one
    @OneToOne
    @JoinColumn(name = "service_time")
    private ServiceTime time;

    //One to one
    @OneToOne
    @JoinColumn(name = "client")
    private Client client;

    //One to one
    @OneToOne
    @JoinColumn(name = "doctor")
    private Doctor doctor;

    public Ticket() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public ServiceTime getTime() {
        return time;
    }

    public void setTime(ServiceTime time) {
        this.time = time;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ticket{");
        sb.append("id=").append(id);
        sb.append(", day=").append(day);
        sb.append(", serviceTime=").append(time);
        sb.append(", client=").append(client);
        sb.append(", doctor=").append(doctor);
        sb.append('}');
        return sb.toString();
    }
}
