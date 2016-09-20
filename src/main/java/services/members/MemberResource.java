package services.members;

import domain.*;
import dto.MemberListWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.PersistenceManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by helen on 29/08/2016.
 */
@Path("service/members")
public class MemberResource {
    @PersistenceContext
    EntityManager em = PersistenceManager.instance().createEntityManager();

    private Executor executor = Executors.newSingleThreadExecutor();
    private List<AsyncResponse> responses = new ArrayList<>();

    private static final Logger _logger = LoggerFactory.getLogger(MemberResource.class);
    private Map<Long, Member> _memberDB = new ConcurrentHashMap<Long, Member>();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public dto.Member getMember(@PathParam("id") long id, @CookieParam("cache")
            NewCookie cookie) {

        boolean ignoreCache = false;
        if (cookie != null && cookie.getValue() != null) {
            ignoreCache = cookie.getValue().equals("ignore-cache");
        }

        Member member;
        if (_memberDB.containsKey(id) && !ignoreCache) {
            member = _memberDB.get(id);
        } else {
            member = em.find(Member.class, id);
        }

        if (member == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            _memberDB.put(member.getId(), member);
        }
        return MemberMapper.toDto(member);
    }

    @GET
    @Path("/{id}/invoice")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public void generateInvoice(@Suspended AsyncResponse response, @PathParam("id") long id) {
        response.setTimeout(100000000000l, TimeUnit.HOURS);
        response.setTimeoutHandler(new TimeoutHandler() {

            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {

            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Member member = em.find(Member.class, id);
                _logger.info(member.toString());
                Invoice invoice = new Invoice(member, member instanceof AUStudent ? InvoicePeriod.SEMESTER : InvoicePeriod.MONTH);
                response.resume(invoice);
            }
        });
    }


    @GET
    @Produces(MediaType.APPLICATION_XML)
    public MemberListWrapper getMembers(@QueryParam("start") int start, @QueryParam("size") int size) {
        Query query = em.createQuery("SELECT m from Member m");
        List<Member> members = query.getResultList();

        List<dto.Member> dtoMembers = members.stream()
                .map(e -> MemberMapper.toDto(e))
                .collect(Collectors.toList());

        String urlNext;
        String urlPrev;
        if (dtoMembers.size() <= 10) {
            urlNext = "";
            urlPrev = "";
            return new MemberListWrapper(dtoMembers, urlNext, urlPrev);
        }

        /**
         * Determine next URL start and size
         */
        int newStartForNext = start + size;
        if (newStartForNext >= dtoMembers.size()) {
            urlNext = "";
        } else {
            urlNext = "http://localhost:8000/service/members?start=" + newStartForNext + "&size=" + size;
        }

        /**
         * Determine previous URL start (may be negative)
         */
        int newStartForPrev = start - size;

        if (start <= 0) {
            urlPrev = "";
        } else {
            urlPrev = "http://localhost:8000/service/members?start=" + newStartForPrev + "&size=" + size;
        }

        int end = newStartForNext;
        if (start < 0) {
            end = start + 10;
            start = 0;
        }

        _logger.info("newStartForNext=" + newStartForNext, " dtoMembersSize=" + dtoMembers.size());
        if (newStartForNext > dtoMembers.size() && newStartForNext - dtoMembers.size() > 0) {
            end = dtoMembers.size();
        }

        _logger.info("start=" + start +  " end=" + end);

        List<dto.Member> wantedMembers = new ArrayList<>();
        for (int i = start; i < end; i++) {
            wantedMembers.add(dtoMembers.get(i));
        }
        MemberListWrapper memberListWrapper = new MemberListWrapper(wantedMembers, urlNext, urlPrev);
        return memberListWrapper;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createMember(
            dto.Member dtoMember) {
        Member member = MemberMapper.toDomainModel(dtoMember);

        em.getTransaction().begin();
        em.persist(member);
        em.getTransaction().commit();


        return Response.created(URI.create("service/members/" + member.getId())).build();
    }

    @POST
    @Consumes("text/plain,text/html")
    public Response createMember(@QueryParam("email") String email, @QueryParam("belt") String belt, @QueryParam("fees") double fees) {


        Member member = new Member(
                email,
                MemberMapper.mapBeltToEnum(belt),
                new Fees(fees)
        );

        em.getTransaction().begin();
        em.persist(member);
        em.getTransaction().commit();


        return Response.created(URI.create("service/members/" + member.getId())).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateMember(
            dto.Member dtoMember) {
        // Get the Parolee object from the database.

        // Update the Parolee object in the database based on the data in
        // dtoMember.
        Member member = MemberMapper.toDomainModel(dtoMember);
        em.merge(member);

        _memberDB.remove(dtoMember.getId());

        return Response.noContent().build();

        // JAX-RS will add the default response code (204 No Content) to the
        // HTTP response message.

    }

    @DELETE
    @Path("/{id}")
    public Response deleteMember(@PathParam("id") long id) {
        Member member = em.find(Member.class, id);

        if(member == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        em.getTransaction().begin();
        em.remove(member);
        em.getTransaction().commit();

        _memberDB.remove(id);

        return Response.noContent().build();
    }

}
