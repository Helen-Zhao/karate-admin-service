package domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by helen on 20/09/2016.
 */
@XmlRootElement
public class Invoice implements Serializable{

    private static final double STUDENT_FEES_PER_SEMESTER = 70.0d;
    private static final double ANNUAL_STUDENT_MEMBERSHIP_FEE = 5.0d;
    private static final double NONSTUDENT_FEES_PER_MONTH = 40.0d;

    @XmlElement
    private Member member;
    @XmlElement
    private InvoicePeriod invoicePeriod;
    @XmlElement
    private double balanceDueThisPeriod;
    @XmlElement
    private double startingBalance;

    public Invoice(Member member, InvoicePeriod invoicePeriod) {
        this.member = member;
        this.invoicePeriod = invoicePeriod;
        this.balanceDueThisPeriod = (member instanceof AUStudent) ? STUDENT_FEES_PER_SEMESTER : NONSTUDENT_FEES_PER_MONTH;
        this.startingBalance = member.getFees().getOutstandingBalance();
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "member=" + member +
                ", invoicePeriod=" + invoicePeriod +
                ", balanceDueThisPeriod=" + balanceDueThisPeriod +
                ", startingBalance=" + startingBalance +
                '}';
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public InvoicePeriod getInvoicePeriod() {
        return invoicePeriod;
    }

    public void setInvoicePeriod(InvoicePeriod invoicePeriod) {
        this.invoicePeriod = invoicePeriod;
    }

    public double getBalanceDueThisPeriod() {
        return balanceDueThisPeriod;
    }

    public void setBalanceDueThisPeriod(double balanceDueThisPeriod) {
        this.balanceDueThisPeriod = balanceDueThisPeriod;
    }



}