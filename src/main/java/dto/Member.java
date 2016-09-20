package dto;

import domain.Fees;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.xml.bind.annotation.*;

/**
 * Created by helen on 30/08/2016.
 * <p>
 * Name: Helen Zhao
 * UPI: hzha587
 * AUID: 6913580
 * <p>
 * SOFTENG 325 ASSIGNMENT 1 MAIN
 */

@XmlType(name = "dtoMember")
@XmlRootElement(name = "member")
@XmlAccessorType(XmlAccessType.FIELD)
public class Member {

    @XmlAttribute(name = "id")
    protected long id;

    @XmlElement(name = "email", required = true)
    protected String email;

    @XmlElement(name = "belt", required = true)
    protected String belt;

    @XmlElement(name = "fees")
    protected Fees fees;


    public Member() {

    }

    public Member(String email,
                  String belt,
                  Fees fees) {
        this.email = email;
        this.belt = belt;
        this.fees = fees;
    }

    public Member(long id, String email,
                  String belt,
                  Fees fees) {
        this.id = id;
        this.email = email;
        this.belt = belt;
        this.fees = fees;

    }

    public Fees getFees() {
        return fees;
    }

    public void setFees(Fees fees) {
        this.fees = fees;
    }

    public long getId() {
        return id;
    }

    public void setId(long _id) {
        this.id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBelt() {
        return belt;
    }

    public void setBelt(String belt) {
        this.belt = belt;
    }


    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Member: { [");
        buffer.append(id);
        buffer.append("]; ");
        if (email != null) {
            buffer.append(email);
            buffer.append("; ");
        }
        if (belt != null) {
            buffer.append(belt);
        }
        buffer.append("; ");
        buffer.append(" }");

        return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Member))
            return false;
        if (obj == this)
            return true;

        Member rhs = (Member) obj;
        return new EqualsBuilder().
                append(id, rhs.id).
                append(email, rhs.email).
                append(belt, rhs.belt).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(id).
                append(email).
                append(belt).
                toHashCode();
    }


}
