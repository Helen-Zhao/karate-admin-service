package services;

import domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
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
    @Produces("")
    public Member getMember(@PathParam("id") long id) {
        Member member = _memberDB.get(id);
        if (member == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return member;
    }
}
