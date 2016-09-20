package services;

import domain.Belt;
import domain.Invoice;
import domain.Member;
import dto.MemberListWrapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.members.MemberMapper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Created by helen on 30/08/2016.
 */
public class MemberWebServiceTest {
    private static final String WEB_SERVICE_URI = "http://localhost:8000/service/members";

    private static final Logger _logger = LoggerFactory.getLogger(MemberWebServiceTest.class);

    private static Client _client;

    /**
     * One-time setup method that creates a Web service client.
     */
    @BeforeClass
    public static void setUpClient() {
        _client = ClientBuilder.newClient();
    }

    /**
     * One-time finalisation method that destroys the Web service client.
     */
    @AfterClass
    public static void destroyClient() {
        _client.close();
    }

    /**
     * Tests that the Web service can create a new Member.
     */

    @Test
    public void addMember() {
        Member member = new Member(
                "lalal@example.com",
                Belt.BLACK_FIRST_DAN
        );

        dto.Member dtoMember = MemberMapper.toDto(member);

        Response response = _client.
                target(WEB_SERVICE_URI)
                .request()
                .post(Entity.entity(dtoMember, MediaType.APPLICATION_XML));

        if (response.getStatus() != 201) {
            fail("Failed to create new Member");
        }

        String location = response.getLocation().toString();
        response.close();

        dto.Member dtoMemberFromService = _client.target(location)
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(dto.Member.class);

        Member memberFromService = MemberMapper.toDomainModel(dtoMemberFromService);

        assertEquals(member.getBelt(), memberFromService.getBelt());
        assertEquals(member.getEmail(), memberFromService.getEmail());
    }

    @Test
    public void updateMember() {
        Member member = new Member(
                "booooo@example.com",
                Belt.YELLOW
        );

        dto.Member dtoMember = MemberMapper.toDto(member);

        Response response = _client.
                target(WEB_SERVICE_URI)
                .request()
                .post(Entity.entity(dtoMember, MediaType.APPLICATION_XML));

        if (response.getStatus() != 201) {
            fail("Failed to create new Member");
        }

        String location = response.getLocation().toString();
        response.close();

        dto.Member dtoMemberFromService = _client.target(location)
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(dto.Member.class);

        Member memberFromService = MemberMapper.toDomainModel(dtoMemberFromService);

        /**
         * Updating
         *
         */

        memberFromService.setBelt(Belt.YELLOW_TAB);

        dtoMember = MemberMapper.toDto(memberFromService);
        Response response1 = _client.
                target(WEB_SERVICE_URI + "/" + dtoMember.getId())
                .request()
                .put(Entity.entity(dtoMember, MediaType.APPLICATION_XML));

        if (response1.getStatus() != 204) {
            fail("Failed to update Member");
        }

        response.close();


        dto.Member updatedDtoMemberFromService = _client.target(WEB_SERVICE_URI + "/" + memberFromService.getId())
                .request()
                .accept(MediaType.APPLICATION_XML)
                .cookie("cache", "ignore-cache")
                .get(dto.Member.class);

        Member updatedMemberFromService = MemberMapper.toDomainModel(updatedDtoMemberFromService);

        assertEquals(memberFromService.getBelt(), updatedMemberFromService.getBelt());
        assertEquals(memberFromService.getEmail(), updatedMemberFromService.getEmail());
    }

    @Test
    public void getAllMembers() {

        MemberListWrapper memberListWrapper = _client.target(WEB_SERVICE_URI + "?start=0&size=10")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(dto.MemberListWrapper.class);

        List<dto.Member> members = memberListWrapper.getQueriedMembers();
        assertTrue(members != null);
        assertTrue(members.size() > 0);
    }

    @Test(expected = WebApplicationException.class)
    public void deleteMember() {
        Member member = new Member(
                "goingToBeDeleted@something.com",
                Belt.BLACK_FOURTH_DAN
        );

        dto.Member dtoMember = MemberMapper.toDto(member);

        Response response = _client.
                target(WEB_SERVICE_URI)
                .request()
                .post(Entity.entity(dtoMember, MediaType.APPLICATION_XML));

        if (response.getStatus() != 201) {
            fail("Failed to create new Member");
        }

        String location = response.getLocation().toString();
        response.close();

        dto.Member dtoMemberFromService = _client.target(location)
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(dto.Member.class);

        /**
         * Member from service now exists and we can delete it with the ID we got back
         */

        Response deleteResponse = _client
                .target(WEB_SERVICE_URI + "/" + dtoMemberFromService.getId())
                .request()
                .delete();

        if (deleteResponse.getStatus() != 204) {
            fail("Failed to delete Member");
        }

        dto.Member shouldntExist = _client.target(WEB_SERVICE_URI + "/" + dtoMemberFromService.getId())
                .request()
                .get(dto.Member.class);

        _logger.info(shouldntExist.getEmail());

    }


    @Test
    public void asyncTest() {
        Member member = new Member(
                "timeToPay@something.com",
                Belt.BLUE
        );

        dto.Member dtoMember = MemberMapper.toDto(member);

        Response response = _client.
                target(WEB_SERVICE_URI)
                .request()
                .post(Entity.entity(dtoMember, MediaType.APPLICATION_XML));

        if (response.getStatus() != 201) {
            fail("Failed to create new Member");
        }

        String location = response.getLocation().toString();
        response.close();

        dto.Member dtoMemberFromService = _client.target(location)
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(dto.Member.class);

        Client client = ClientBuilder.newClient();

        final Future<Response> responseFuture = client
                .target(WEB_SERVICE_URI + "/" + dtoMemberFromService.getId() + "/invoice").request()
                .async()
                .get();

        try {
            Response responseNow = responseFuture.get();
            Invoice invoice = responseNow.readEntity(Invoice.class);
            assertEquals(member.getEmail(), invoice.getMember().getEmail());
            assertEquals(member.getBelt(), invoice.getMember().getBelt());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
