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
    private Map<Date, Grading> GradingDB = new ConcurrentHashMap<Date, Grading>();

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

        Grading Grading;
        //Determine if Grading exists in cache
        if (GradingDB.containsKey(date) && !ignoreCache) {
            Grading = GradingDB.get(date);
        } else {
            Grading = em.find(Grading.class, date);
            GradingDB.put(Grading.getDate(), Grading);
        }

        if (Grading == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return Grading;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createGrading(
            Grading Grading) {



        em.getTransaction().begin();
        em.persist(Grading);
        em.getTransaction().commit();


        return Response.created(URI.create("service/Gradings/" + sdf.format(Grading.getDate()))).build();
    }

    @PUT
    @Path("/{date}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateGrading(
            Grading Grading) {
        em.merge(Grading);

        return Response.noContent().build();

        // JAX-RS will add the default response code (204 No Content) to the
        // HTTP response message.

    }
}