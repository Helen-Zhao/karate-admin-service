package domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by helen on 29/08/2016.
 */

@Entity
@Table(name = "MEMBERS")
public class Member implements Serializable {

    private long _memberId;
    private String memEmail;
    private Belt belt;
    private int attendanceThisYear;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long get_memberId() {
        return this._memberId;
    }

    public void set_memberId(long _memberId) {
        this._memberId = _memberId;
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


