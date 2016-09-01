package services;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by helen on 29/08/2016.
 */
public class MemberApplication  extends Application {

    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> classes = new HashSet<>();

    public MemberApplication() {

        MemberResource mr = new MemberResource();
        singletons.add(mr);

        classes.add(MemberResolver.class);
    }

    @Override
    public Set<Object> getSingletons() {
        return this.singletons;
    }

    @Override
    public Set<Class<?>> getClasses() {
        return this.classes;
    }
}
