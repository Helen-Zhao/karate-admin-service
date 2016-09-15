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
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by helen on 29/08/2016.
 */
@Path("/students")
public class AUStudentResource {
    @PersistenceContext
    EntityManager em = PersistenceManager.instance().createEntityManager();


    private static final Logger _logger = LoggerFactory.getLogger(AUStudentResource.class);
    private Map<Long, AUStudent> _AUStudentDB = new ConcurrentHashMap<Long, AUStudent>();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public dto.AUStudent getAUStudent(@PathParam("id") long id) {
        AUStudent auStudent;
        if (_AUStudentDB.containsKey(id)) {
            auStudent = _AUStudentDB.get(id);
        } else {
            auStudent = em.find(AUStudent.class, id);
            _AUStudentDB.put(auStudent.getId(), auStudent);
        }

        if (auStudent == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
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
        System.out.println("Read AUStudent: " + dtoAUStudent);
        AUStudent AUStudent = AUStudentMapper.toDomainModel(dtoAUStudent);

        em.getTransaction().begin();
        em.persist(AUStudent);
        em.getTransaction().commit();


        return Response.created(URI.create("students/" + AUStudent.getId())).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateAUStudent(
            dto.AUStudent dtoAUStudent) {
        // Get the Parolee object from the database.

        // Update the Parolee object in the database based on the data in
        // dtoAUStudent.
        AUStudent AUStudent = AUStudentMapper.toDomainModel(dtoAUStudent);
        em.merge(AUStudent);

        return Response.noContent().build();

        // JAX-RS will add the default response code (204 No Content) to the
        // HTTP response message.

    }

}
