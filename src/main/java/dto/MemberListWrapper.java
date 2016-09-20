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

    @XmlElement(name = "urlNext")
    protected String urlNext;

    @XmlElement (name = "urlPrev")
    protected String urlPrev;

    public MemberListWrapper() {

    }

    public MemberListWrapper(List<Member> queriedMembers, String urlNext, String urlPrev) {
        this.queriedMembers = queriedMembers;
        this.urlNext = urlNext;
        this.urlPrev = urlPrev;
    }


    public void setQueriedMembers(List<Member> queriedMembers) {
        this.queriedMembers = queriedMembers;
    }

    public List<Member> getQueriedMembers() {
        return queriedMembers;
    }

    public String getUrlNext() {
        return urlNext;
    }

    public void setUrlNext(String urlNext) {
        this.urlNext = urlNext;
    }

    public String getUrlPrev() {
        return urlPrev;
    }

    public void setUrlPrev(String urlPrev) {
        this.urlPrev = urlPrev;
    }

}
