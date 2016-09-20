package services.sessions;

import domain.Member;
import domain.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.PersistenceManager;
import services.members.MemberMapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Path("service/sessions")
public class SessionResource {
    @PersistenceContext
    EntityManager em = PersistenceManager.instance().createEntityManager();

    public SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");

    private static final Logger _logger = LoggerFactory.getLogger(services.members.MemberResource.class);
    private Map<Date, Session> sessionDB = new ConcurrentHashMap<Date, Session>();

    @GET
    @Path("/{date}")
    @Produces(MediaType.APPLICATION_XML)
    public Session getSession(@PathParam("date") String strDate, @CookieParam("cache")
            NewCookie cookie) {

        //Determine if date param is valid
        Date date;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        boolean ignoreCache = false;
        if (cookie != null && cookie.getValue() != null) {
            ignoreCache = cookie.getValue().equals("ignore-cache");
        }

        Session session;
        //Determine if session exists in cache
        if (sessionDB.containsKey(date) && !ignoreCache) {
            session = sessionDB.get(date);
        } else {
            session = em.find(Session.class, date);
        }

        if (session == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            sessionDB.put(session.getDate(), session);
        }
        return session;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createSession(
            Session session) {

        Session existingSession = em.find(Session.class, session.getDate());

        if (existingSession == null) {
            em.getTransaction().begin();
            em.persist(session);
            em.getTransaction().commit();
        } else {
            em.merge(session);
            sessionDB.remove(session.getDate());
        }

        return Response.created(URI.create("service/sessions/" + sdf.format(session.getDate()))).build();
    }

    @PUT
    @Path("/{date}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateSession(
            Session session) {
        em.merge(session);

        sessionDB.remove(session.getDate());

        return Response.noContent().build();

        // JAX-RS will add the default response code (204 No Content) to the
        // HTTP response message.

    }

    @DELETE
    @Path("/{date}")
    public Response deleteSession(@PathParam("date") String strDate) {
        Session session;
        Date date;
        try {
            date = sdf.parse(strDate);
            session = em.find(Session.class, date);
        } catch (ParseException pe) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if(session == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            sessionDB.remove(session.getDate());
        }

        em.getTransaction().begin();
        em.remove(session);
        em.getTransaction().commit();

        return Response.noContent().build();
    }
}