package services.sessions;

import domain.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.PersistenceManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Path("/sessions")
public class SessionResource {
    @PersistenceContext
    EntityManager em = PersistenceManager.instance().createEntityManager();

    public SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");

    private static final Logger _logger = LoggerFactory.getLogger(services.members.MemberResource.class);
    private Map<Date, Session> sessionDB = new ConcurrentHashMap<Date, Session>();

    @GET
    @Path("/{date}")
    @Produces(MediaType.APPLICATION_XML)
    public Session getMember(@PathParam("date") String strDate) {
        Session session;
        Date date;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
            if (sessionDB.containsKey(date)) {
            session = sessionDB.get(date);
        } else {
            session = em.find(Session.class, date);
            sessionDB.put(session.getDate(), session);
        }

        if (session == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return session;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createSession(
            Session session) {

        em.getTransaction().begin();
        em.persist(session);
        em.getTransaction().commit();


        return Response.created(URI.create("sessions/" + sdf.format(session.getDate()))).build();
    }
}