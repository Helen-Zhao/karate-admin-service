package services;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by helen on 29/08/2016.
 */
public class MemberApplication  extends Application {

    @PersistenceUnit
    EntityManagerFactory emf;
    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> classes = new HashSet<>();

    public MemberApplication() {

        MemberResource mr = new MemberResource();
        AUStudentResource asr = new AUStudentResource();
        singletons.add(mr);
        singletons.add(asr);
        singletons.add(PersistenceManager.instance());

        classes.add(MemberResolver.class);
        classes.add(AUStudentResolver.class);
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
