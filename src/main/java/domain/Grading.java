package domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by helen on 15/09/2016.
 */

@Entity
@Table(name = "GRADING")
@XmlType(name = "grading")
@XmlRootElement(name = "grading")
@XmlAccessorType(XmlAccessType.FIELD)
public class Grading extends Session implements Serializable {

    @XmlElementWrapper(name = "gradedMembers")
    @XmlElement(name = "gradedMember")
    @OneToMany
    private List<Member> gradedMembers;

    @XmlElementWrapper(name = "doubleGradedMembers")
    @XmlElement(name = "doubleGradedMember")
    @OneToMany
    private List<Member> doubleGradedMembers;

    public Grading() {

    }

    public Grading(Date date) {
        this.date = date;
        this.attendees = new ArrayList<>();
        this.gradedMembers = new ArrayList<>();
    }

    private void gradeMembers() {
        for (Member m : gradedMembers) {
            m.setBelt(Belt.values()[m.getBelt().ordinal() + 1]);
        }
    }

    private void doubleGradeMembers() {
        for (Member m : gradedMembers) {
            m.setBelt(Belt.values()[m.getBelt().ordinal() + 2]);
        }
    }

    private void chargeGradingFees() {
        for (Member m : this.getAttendees()) {
            m.getFees().chargeFees(35.0d);
        }
    }

}
