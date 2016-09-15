package services;

import services.members.AUStudentResolver;
import services.members.AUStudentResource;
import services.members.MemberResolver;
import services.members.MemberResource;
import services.sessions.SessionResolver;
import services.sessions.SessionResource;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by helen on 29/08/2016.
 */
public class KarateAdminApplication extends Application {

    @PersistenceUnit
    EntityManagerFactory emf;
    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> classes = new HashSet<>();

    public KarateAdminApplication() {

        MemberResource mr = new MemberResource();
        AUStudentResource asr = new AUStudentResource();
        SessionResource sr = new SessionResource();
        singletons.add(sr);
        singletons.add(mr);
        singletons.add(asr);
        singletons.add(PersistenceManager.instance());

        classes.add(MemberResolver.class);
        classes.add(AUStudentResolver.class);
        classes.add(SessionResolver.class);
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
