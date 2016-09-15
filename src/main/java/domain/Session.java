package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.List;

/**
 * Created by helen on 15/09/2016.
 */

@Entity
public class Session implements Serializable{
    @Id
    private Date date;

    @ManyToMany
    private List<Member> attendees;

    public Session() {
        this.date = new Date();
        this.attendees = new ArrayList<>();
    }

    public Session(Date date) {
        this.date = date;
        this.attendees = new ArrayList<>();
    }

    public Session(Date date, List<Member> attendees) {
        this.date = date;
        this.attendees = attendees;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Member> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Member> attendees) {
        this.attendees = attendees;
    }


}
