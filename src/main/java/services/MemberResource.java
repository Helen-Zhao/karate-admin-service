package services;

import domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by helen on 29/08/2016.
 */

@Path("/members")
public class MemberResource {
    private static final Logger _logger = LoggerFactory.getLogger(MemberResource.class);
    private Map<Long, Member> _memberDB = new ConcurrentHashMap<Long, Member>();
    private AtomicInteger _idCounter = new AtomicInteger();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public dto.Member getMember(@PathParam("id") long id) {
        Member member = _memberDB.get(id);
        if (member == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return MemberMapper.toDto(member);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createMember(
            dto.Member dtoMember) {
        System.out.println("Read member: " + dtoMember);
        Member member = MemberMapper.toDomainModel(dtoMember);
        member.setId(_idCounter.incrementAndGet());
        _memberDB.put(member.getId(), member);

        System.out.println("Created member: " + member.toString());

        // Return a Response that specifies a status code of 201 Created along
        // with the Location header set to URI of the newly created Parolee.
//        return Response.created(URI.create("/members/" + member.getId())).entity(MemberMapper.toDto(member))
//                .build();
        return Response.created(URI.create("members/" + member.getId())).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void updateMember(
            dto.Member dtoMember) {
        // Get the Parolee object from the database.

        // Update the Parolee object in the database based on the data in
        // dtoMember.
        Member member = MemberMapper.toDomainModel(dtoMember);
        // JAX-RS will add the default response code (204 No Content) to the
        // HTTP response message.
        //TODO
    }

}
