package dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;

/**
 * Created by helen on 30/08/2016.
 */

@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
public class AUStudent extends Member{

    @XmlElement(name = "auid", required = true)
    private int _auid;

    @XmlElement(name = "upi", required = true)
    private String _upi;

    @XmlElement(name = "paidAnnualFee", required = true)
    private boolean _paidAnnualFee;

    public AUStudent() {

    }

    public AUStudent(String email,
                     String belt,
                     int attendanceCount,
                     List<Date> attendance,
                     int auid,
                     String upi,
                     boolean paidAnnualFee) {
        this.set_email(email);
        this.set_belt(belt);
        this.set_attendanceThisYear(attendanceCount);
        this.setAttendance(attendance);
        this._auid = auid;
        this._upi = upi;
        this._paidAnnualFee = paidAnnualFee;
    }

    public AUStudent(long id,
                     String email,
                     String belt,
                     int attendanceCount,
                     List<Date> attendance,
                     int auid,
                     String upi,
                     boolean paidAnnualFee) {
        this.setId(id);
        this.set_email(email);
        this.set_belt(belt);
        this.set_attendanceThisYear(attendanceCount);
        this.setAttendance(attendance);
        this._auid = auid;
        this._upi = upi;
        this._paidAnnualFee = paidAnnualFee;
    }

    public int get_auid() {
        return _auid;
    }

    public void set_auid(int _auid) {
        this._auid = _auid;
    }

    public String get_upi() {
        return _upi;
    }

    public void set_upi(String _upi) {
        this._upi = _upi;
    }

    public boolean get_paidAnnualFee() {
        return _paidAnnualFee;
    }

    public void set_paidAnnualFee(boolean _paidAnnualFee) {
        this._paidAnnualFee = _paidAnnualFee;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Member: { [");
        buffer.append(this.getId());
        buffer.append("]; ");
        if (this.get_email() != null) {
            buffer.append(this.get_email());
            buffer.append("; ");
        }
        if (this.get_belt() != null) {
            buffer.append(this.get_belt());
        }
        buffer.append("; ");
        buffer.append(this.get_attendanceThisYear());
        buffer.append("; ");
        buffer.append(this.getAttendance());
        buffer.append("; ");
        buffer.append(_auid);
        buffer.append("; ");
        buffer.append(_upi);
        buffer.append("; ");
        buffer.append(_paidAnnualFee);
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
                append(this.getId(), rhs.getId()).
                append(this.get_email(), rhs.get_email()).
                append(this.get_belt(), rhs.get_belt()).
                append(this.get_attendanceThisYear(), rhs.get_attendanceThisYear()).
                append(this.getAttendance(), rhs.getAttendance()).
                append(_auid, rhs._auid).
                append(_upi, rhs._upi).
                append(_paidAnnualFee, rhs._paidAnnualFee).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(this.getId()).
                append(this.get_email()).
                append(this.get_belt()).
                append(this.get_attendanceThisYear()).
                append(this.getAttendance()).
                append(_auid).
                append(_upi).
                append(_paidAnnualFee).
                toHashCode();
    }


}
