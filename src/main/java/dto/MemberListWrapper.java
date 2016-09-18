package dto;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by helen on 19/09/2016.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MemberListWrapper implements Serializable{

    @XmlElementWrapper(name = "queriedMembers")
    @XmlElement(name = "queriedMember")
    protected List<Member> queriedMembers;

    @XmlElement
    protected int totalListSize;

    public MemberListWrapper() {

    }

    public MemberListWrapper(List<Member> queriedMembers) {
        this.queriedMembers = queriedMembers;
        this.totalListSize = queriedMembers.size();
    }


    public void setQueriedMembers(List<Member> queriedMembers) {
        this.queriedMembers = queriedMembers;
    }

    public int getTotalListSize() {
        return totalListSize;
    }

    public void setTotalListSize(int totalListSize) {
        this.totalListSize = totalListSize;
    }
    public List<Member> getQueriedMembers() {
        return queriedMembers;
    }
}
