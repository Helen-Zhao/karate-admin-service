package domain;

import org.apache.commons.lang3.time.DateUtils;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * Created by helen on 15/09/2016.
 */

@Entity
@XmlRootElement
@XmlSeeAlso({Grading.class})
@XmlType(name = "session")
@XmlAccessorType(XmlAccessType.FIELD)
@Inheritance(strategy = InheritanceType.JOINED)
public class Session implements Serializable{

    @Id
    @XmlAttribute
    protected Date date;

    @XmlElementWrapper(name = "attendees")
    @XmlElement(name = "attendee")
    @OneToMany(cascade = CascadeType.PERSIST)
    protected List<Member> attendees;

    public Session() {
        this.date = DateUtils.ceiling(new Date(), Calendar.DAY_OF_MONTH);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;

        Session session = (Session) o;

        if (!date.equals(session.date)) return false;
        return attendees.equals(session.attendees);

    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + attendees.hashCode();
        return result;
    }
}
