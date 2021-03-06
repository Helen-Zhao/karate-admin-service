package services.members;

import domain.AUStudent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.PersistenceManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by helen on 29/08/2016.
 * <p>
 * Name: Helen Zhao
 * UPI: hzha587
 * AUID: 6913580
 * <p>
 * SOFTENG 325 ASSIGNMENT 1 MAIN
 */
@Path("service/students")
public class AUStudentResource {
    @PersistenceContext
    EntityManager em = PersistenceManager.instance().createEntityManager();


    private static final Logger _logger = LoggerFactory.getLogger(AUStudentResource.class);
    private Map<Long, AUStudent> _AUStudentDB = new ConcurrentHashMap<Long, AUStudent>();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public dto.AUStudent getAUStudent(@PathParam("id") long id, @CookieParam("cache") NewCookie cookie) {
        boolean ignoreCache = false;
        if (cookie != null && cookie.getValue() != null) {
            ignoreCache = cookie.getValue().equals("ignore-cache");
        }

        AUStudent auStudent;
        if (_AUStudentDB.containsKey(id) && !ignoreCache) {
            auStudent = _AUStudentDB.get(id);
        } else {
            auStudent = em.find(AUStudent.class, id);
        }

        if (auStudent == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            _AUStudentDB.put(auStudent.getId(), auStudent);
        }
        return AUStudentMapper.toDto(auStudent);
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<dto.AUStudent> getAUStudents() {
        Query query = em.createQuery("SELECT m from AUStudent m");
        List<AUStudent> AUStudents = query.getResultList();
        List<dto.AUStudent> dtoAUStudents = AUStudents.stream()
                .map(e -> AUStudentMapper.toDto(e))
                .collect(Collectors.toList());
        return dtoAUStudents;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createAUStudent(
            dto.AUStudent dtoAUStudent) {
        AUStudent AUStudent = AUStudentMapper.toDomainModel(dtoAUStudent);

        em.getTransaction().begin();
        em.persist(AUStudent);
        em.getTransaction().commit();

        return Response.created(URI.create("service/students/" + AUStudent.getId())).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateAUStudent(
            dto.AUStudent dtoAUStudent) {

        AUStudent AUStudent = AUStudentMapper.toDomainModel(dtoAUStudent);
        em.merge(AUStudent);

        _AUStudentDB.remove(dtoAUStudent.getId());

        return Response.noContent().build();

    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") long id) {
        AUStudent auStudent = em.find(AUStudent.class, id);

        if (auStudent == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        em.getTransaction().begin();
        em.remove(auStudent);
        em.getTransaction().commit();

        _AUStudentDB.remove(id);

        return Response.noContent().build();
    }


}
