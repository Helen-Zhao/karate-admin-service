package dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.xml.bind.annotation.*;

/**
 * Created by helen on 30/08/2016.
 */

@XmlRootElement(name = "member")
@XmlAccessorType(XmlAccessType.FIELD)
public class Member {

    @XmlAttribute(name = "id")
    private long _id;

    @XmlElement(name = "email", required = true)
    private String _email;

    @XmlElement(name = "belt", required = true)
    private String _belt;

    @XmlElement(name = "attendance", required = true)
    private int _attendanceThisYear;

    public Member() {

    }

    public Member(String email,
                  String belt,
                  int attendance) {
        this._email = email;
        this._belt = belt;
        this._attendanceThisYear = attendance;
    }

    public Member(long id, String email,
                  String belt,
                  int attendance) {
        this._id = id;
        this._email = email;
        this._belt = belt;
        this._attendanceThisYear = attendance;
    }

    public long getId() {
        return _id;
    }

    public void setId(long _id) {
        this._id = _id;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_belt() {
        return _belt;
    }

    public void set_belt(String _belt) {
        this._belt = _belt;
    }

    public int get_attendanceThisYear() {
        return _attendanceThisYear;
    }

    public void set_attendanceThisYear(int _attendanceThisYear) {
        this._attendanceThisYear = _attendanceThisYear;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Parolee: { [");
        buffer.append(_id);
        buffer.append("]; ");
        if (_email != null) {
            buffer.append(_email);
            buffer.append("; ");
        }
        if (_belt != null) {
            buffer.append(_belt);
        }
        buffer.append("; ");
        buffer.append(_attendanceThisYear);
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
                append(_id, rhs._id).
                append(_email, rhs._email).
                append(_belt, rhs._belt).
                append(_attendanceThisYear, rhs._attendanceThisYear).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(_id).
                append(_email).
                append(_belt).
                append(_attendanceThisYear).
                toHashCode();
    }


}
