package domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by helen on 29/08/2016.
 * <p>
 * Name: Helen Zhao
 * UPI: hzha587
 * AUID: 6913580
 * <p>
 * SOFTENG 325 ASSIGNMENT 1 MAIN
 */

@Entity
@Table(name = "STUDENTS")
public class AUStudent extends Member implements Serializable {

    private int auid;
    private String upi;
    private boolean paidAnnualFee;

    public AUStudent() {
    }


    public AUStudent(String email, Belt belt, Fees fees, int auid, String upi, boolean paidAnnualFee) {
        this.email = email;
        this.belt = belt;
        this.fees = fees;
        this.auid = auid;
        this.upi = upi;
        this.paidAnnualFee = paidAnnualFee;
    }

    public AUStudent(String email, Belt belt, int auid, String upi, boolean paidAnnualFee) {
        this.email = email;
        this.belt = belt;
        this.fees = new Fees();
        this.auid = auid;
        this.upi = upi;
        this.paidAnnualFee = paidAnnualFee;
    }


    public AUStudent(long id, String email, Belt belt, Fees fees, int auid, String upi, boolean paidAnnualFee) {
        this.setId(id);
        this.email = email;
        this.belt = belt;
        this.fees = fees;
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

