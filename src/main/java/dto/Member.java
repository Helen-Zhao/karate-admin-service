package dto;

import javax.xml.bind.annotation.*;

/**
 * Created by helen on 30/08/2016.
 */

@XmlRootElement(name = "member")
@XmlAccessorType(XmlAccessType.FIELD)
public class Member {

    @XmlAttribute(name = "id")
    private long _id;

    @XmlElement(name = "email")
    private String _email;

    @XmlElement(name = "belt")
    private String _belt;

    @XmlElement(name = "attendance")
    private int _attendanceThisYear;

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

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
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


}
