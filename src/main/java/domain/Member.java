package domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by helen on 29/08/2016.
 */

@Entity
@Table(name = "MEMBERS")
public class Member implements Serializable {

    private int memberId;
    private String memEmail;
    private String belt;
    private int attendanceThisYear;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemEmail() {
        return memEmail;
    }

    public void setMemEmail(String memEmail) {
        this.memEmail = memEmail;
    }

    public String getBelt() {
        return belt;
    }

    public void setBelt(String belt) {
        this.belt = belt;
    }

    public int getAttendanceThisYear() {
        return attendanceThisYear;
    }

    public void setAttendanceThisYear(int attendanceThisYear) {
        this.attendanceThisYear = attendanceThisYear;
    }

}
