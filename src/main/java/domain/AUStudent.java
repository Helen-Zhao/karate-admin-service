package domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by helen on 29/08/2016.
 */

@Entity
@Table(name = "STUDENTS")
public class AUStudent extends Member implements Serializable {

    private int auid;
    private String upi;
    private boolean paidAnnualFee;

    public AUStudent(String email, Belt belt, int attendance, int auid, String upi, boolean paidAnnualFee) {
        this.setMemEmail(email);
        this.setBelt(belt);
        this.setAttendanceThisYear(attendance);
        this.auid = auid;
        this.upi = upi;
        this.paidAnnualFee = paidAnnualFee;
    }

    public AUStudent(long id, String email, Belt belt, int attendance, int auid, String upi, boolean paidAnnualFee) {
        this.setId(id);
        this.setMemEmail(email);
        this.setBelt(belt);
        this.setAttendanceThisYear(attendance);
        this.auid = auid;
        this.upi = upi;
        this.paidAnnualFee = paidAnnualFee;
    }

    public int getAuid() {
        return auid;
    }

    public void setAuid(int auid) {
        this.auid = auid;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public boolean isPaidAnnualFee() {
        return paidAnnualFee;
    }

    public void setPaidAnnualFee(boolean paidAnnualFee) {
        this.paidAnnualFee = paidAnnualFee;
    }

}

