package domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by helen on 29/08/2016.
 */

@Entity
@Table(name = "MEMBERS")
public class Member implements Serializable {

    private long id;
    private String memEmail;
    private Belt belt;
    private int attendanceThisYear;

    protected Member() {

    }

    public Member(
            long id,
            String email,
            Belt belt,
            int attendanceThisYear) {

        this.id = id;
        this.memEmail = email;
        this.belt = belt;
        this.attendanceThisYear = attendanceThisYear;

    }

    public Member(
            String email,
            Belt belt,
            int attendanceThisYear) {

        this.memEmail = email;
        this.belt = belt;
        this.attendanceThisYear = attendanceThisYear;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public int getAttendanceThisYear() {
        return attendanceThisYear;
    }

    public void setAttendanceThisYear(int attendanceThisYear) {
        this.attendanceThisYear = attendanceThisYear;
    }

    public enum Belt {
        BLACK_FOURTH_DAN,
        BLACK_THIRD_DAN,
        BLACK_SECOND_DAN,
        BLACK_FIRST_DAN,
        BROWN_TWO_TAB,
        BROWN_ONE_TAB,
        BROWN,
        GREEN_TAB,
        GREEN,
        YELLOW_TAB,
        YELLOW,
        BLUE_TAB,
        BLUE,
        WHITE_TAB,
        WHITE
    }
}


