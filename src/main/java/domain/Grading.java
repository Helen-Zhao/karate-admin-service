package domain;

import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

/**
 * Created by helen on 15/09/2016.
 */
public class Grading extends Session implements Serializable {

    @OneToMany
    private List<Member> gradedMembers;
}
