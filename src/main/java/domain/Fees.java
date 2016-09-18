package domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by helen on 15/09/2016.
 */

@Embeddable
public class Fees implements Serializable {

    private double outstandingBalance;

    public Fees() {
        outstandingBalance = 0.0d;
    }

    public Fees(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }


    void chargeFees(double toCharge) {
        outstandingBalance += toCharge;
    }

    public double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }
}
