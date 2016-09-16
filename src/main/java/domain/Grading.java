package domain;

import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by helen on 15/09/2016.
 */
public class Grading extends Session implements Serializable {

    @XmlElement(name = "gradedMembers")
    @OneToMany
    private List<Member> gradedMembers;

    @XmlElement(name = "doubleGradedMembers")
    @OneToMany
    private List<Member> doubleGradedMembers;

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
