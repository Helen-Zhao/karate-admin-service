package dto;

import domain.Fees;
import domain.Session;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by helen on 30/08/2016.
 */

@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
public class AUStudent extends Member{

    @XmlElement(name = "auid", required = true)
    private int auid;

    @XmlElement(name = "upi", required = true)
    private String upi;

    @XmlElement(name = "paidAnnualFee", required = true)
    private boolean paidAnnualFee;


    public AUStudent() {

    }

    public AUStudent(String email,
                     String belt,
                     Fees fees,
                     int auid,
                     String upi,
                     boolean paidAnnualFee) {
        this.email = email;
        this.belt = belt;
        this.auid = auid;
        this.upi = upi;
        this.paidAnnualFee = paidAnnualFee;
    }

    public AUStudent(long id,
                     String email,
                     String belt,
                     Fees fees,
                     int auid,
                     String upi,
                     boolean paidAnnualFee) {

        this.id = id;
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

    public boolean getPaidAnnualFee() {
        return paidAnnualFee;
    }

    public void setPaidAnnualFee(boolean paidAnnualFee) {
        this.paidAnnualFee = paidAnnualFee;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Member: { [");
        buffer.append(id);
        buffer.append("]; ");
        if (this.email != null) {
            buffer.append(email);
            buffer.append("; ");
        }
        if (this.belt != null) {
            buffer.append(belt);
        }
        buffer.append("; ");
        buffer.append(auid);
        buffer.append("; ");
        buffer.append(upi);
        buffer.append("; ");
        buffer.append(paidAnnualFee);
        buffer.append("; ");
        buffer.append(" }");

        return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AUStudent))
            return false;
        if (obj == this)
            return true;

        AUStudent rhs = (AUStudent) obj;
        return new EqualsBuilder().
                append(this.id, rhs.id).
                append(this.email, rhs.email).
                append(this.belt, rhs.belt).
                append(auid, rhs.auid).
                append(upi, rhs.upi).
                append(paidAnnualFee, rhs.paidAnnualFee).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(this.id).
                append(this.email).
                append(this.belt).
                append(auid).
                append(upi).
                append(paidAnnualFee).
                toHashCode();
    }


}
