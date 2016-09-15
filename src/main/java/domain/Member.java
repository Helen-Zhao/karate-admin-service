package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by helen on 29/08/2016.
 */

@Entity
@Table(name = "MEMBERS")
@Inheritance(strategy = InheritanceType.JOINED)

public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    protected String email;

    @Enumerated(EnumType.STRING)
    protected Belt belt;

    @ManyToMany
    protected List<Session> attendedSessions;


    @Embedded
    protected Fees fees;

    protected Member() {

    }

    public Member(
            long id,
            String email,
            Belt belt,
            List<Session> attendedSessions,
            Fees fees) {

        this.id = id;
        this.email = email;
        this.belt = belt;
        this.attendedSessions = attendedSessions;
        this.fees = fees;

    }

    public Member(
            String email,
            Belt belt,
            List<Session> attendedSessions,
            Fees fees) {

        this.email = email;
        this.belt = belt;
        this.attendedSessions = attendedSessions;
        this.fees = fees;

    }

    public Member(
            String email,
            Belt belt
    ) {
        this.email = email;
        this.belt = belt;
        this.attendedSessions = new ArrayList<>();
        this.fees = new Fees();
    }

    public Fees getFees() {
        return fees;
    }

    public void setFees(Fees fees) {
        this.fees = fees;
    }

    public List<Session> getAttendedSessions() {
        return attendedSessions;
    }

    public void setAttendedSessions(List<Session> attendedSessions) {
        this.attendedSessions = attendedSessions;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long _memberId) {
        this.id = _memberId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String memEmail) {
        this.email = memEmail;
    }

    public Belt getBelt() {
        return belt;
    }

    public void setBelt(Belt belt) {
        this.belt = belt;
    }

}


