package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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
    private long id;
    private String memEmail;
    
    @Enumerated(EnumType.STRING)
    private Belt belt;
    private int attendanceCount;

    @ElementCollection
    List<Date> attendance;

    protected Member() {

    }

    public Member(
            long id,
            String email,
            Belt belt,
            int attendanceCount,
            List<Date> attendance) {

        this.id = id;
        this.memEmail = email;
        this.belt = belt;
        this.attendanceCount = attendanceCount;
        this.attendance = attendance;

    }

    public Member(
            String email,
            Belt belt,
            int attendanceCount,
            List<Date> attendance) {

        this.memEmail = email;
        this.belt = belt;
        this.attendanceCount = attendanceCount;
        this.attendance = attendance;

    }


    public List<Date> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Date> attendance) {
        this.attendance = attendance;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long _memberId) {
        this.id = _memberId;
    }

    public String getMemEmail() {
        return memEmail;
    }

    public void setMemEmail(String memEmail) {
        this.memEmail = memEmail;
    }

    public Belt getBelt() {
        return belt;
    }

    public void setBelt(Belt belt) {
        this.belt = belt;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public void setAttendanceCount(int attendanceThisYear) {
        this.attendanceCount = attendanceThisYear;
    }

}


