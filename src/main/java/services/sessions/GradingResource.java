package services.sessions;

import domain.Grading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.PersistenceManager;

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

@Path("service/gradings")
public class GradingResource {
    @PersistenceContext
    EntityManager em = PersistenceManager.instance().createEntityManager();

    public SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");

    private static final Logger _logger = LoggerFactory.getLogger(services.members.MemberResource.class);
    private Map<Date, Grading> gradingDB = new ConcurrentHashMap<Date, Grading>();

    @GET
    @Path("/{date}")
    @Produces(MediaType.APPLICATION_XML)
    public Grading getMember(@PathParam("date") String strDate, @CookieParam("cache")
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
        Grading grading;
        //Determine if Grading exists in cache
        if (gradingDB.containsKey(date) && !ignoreCache) {
            grading = gradingDB.get(date);
        } else {
            grading = em.find(Grading.class, date);
        }

        if (grading == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            gradingDB.put(grading.getDate(), grading);
        }
        return grading;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createGrading(
            Grading grading) {
        Grading existingGrading = em.find(Grading.class, grading.getDate());

        if (existingGrading == null) {
            em.getTransaction().begin();
            em.persist(grading);
            em.getTransaction().commit();
        } else {
            em.merge(grading);
            gradingDB.remove(grading.getDate());
        }


        return Response.created(URI.create("service/gradings/" + sdf.format(grading.getDate()))).build();
    }

    @PUT
    @Path("/{date}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateGrading(
            Grading grading) {
        em.merge(grading);

        gradingDB.remove(grading.getDate());

        return Response.noContent().build();

        // JAX-RS will add the default response code (204 No Content) to the
        // HTTP response message.

    }


    @DELETE
    @Path("/{date}")
    public Response deleteGrading(@PathParam("date") String strDate) {
        Grading grading;
        Date date;
        try {
            date = sdf.parse(strDate);
            grading = em.find(Grading.class, date);
        } catch (ParseException pe) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if(grading == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            gradingDB.remove(grading.getDate());
        }

        em.getTransaction().begin();
        em.remove(grading);
        em.getTransaction().commit();

        return Response.noContent().build();
    }
}