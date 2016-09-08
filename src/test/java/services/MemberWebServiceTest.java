package services;

import domain.Member;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Created by helen on 30/08/2016.
 */
public class MemberWebServiceTest {
    private static final String WEB_SERVICE_URI = "http://localhost:8000/members";

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
     * Runs before each unit test to restore Web service database. This ensures
     * that each test is independent; each test runs on a Web service that has
     * been initialised with a common set of Parolees.
     */
//    @Before
//    public void reloadServerData() {
//        Response response = _client
//                .target(WEB_SERVICE_URI).request()
//                .put(null);
//        response.close();
//
//        // Pause briefly before running any tests. Test addParoleeMovement(),
//        // for example, involves creating a timestamped value (a movement) and
//        // having the Web service compare it with data just generated with
//        // timestamps. Joda's Datetime class has only millisecond precision,
//        // so pause so that test-generated timestamps are actually later than
//        // timestamped values held by the Web service.
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//        }
//    }

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
                Member.Belt.BLACK_FIRST_DAN,
                100
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

        assertEquals(member.getAttendanceThisYear(), memberFromService.getAttendanceThisYear());
        assertEquals(member.getBelt(), memberFromService.getBelt());
        assertEquals(member.getMemEmail(), memberFromService.getMemEmail());
    }

    @Test
    public void updateMember() {
        Member member = new Member(
                "booooo@example.com",
                Member.Belt.YELLOW,
                25
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

        memberFromService.setBelt(Member.Belt.YELLOW_TAB);
        memberFromService.setAttendanceThisYear(memberFromService.getAttendanceThisYear() + 1);

        dtoMember = MemberMapper.toDto(memberFromService);
        Response response1 = _client.
                target(WEB_SERVICE_URI + "/" + dtoMember.getId())
                .request()
                .put(Entity.entity(dtoMember, MediaType.APPLICATION_XML));

        if (response.getStatus() != 201) {
            fail("Failed to create new Member");
        }

        location = response.getLocation().toString();
        response.close();

        dto.Member updatedDtoMemberFromService = _client.target(location)
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(dto.Member.class);

        Member updatedMemberFromService = MemberMapper.toDomainModel(updatedDtoMemberFromService);


        assertEquals(memberFromService.getAttendanceThisYear(), updatedMemberFromService.getAttendanceThisYear());
        assertEquals(memberFromService.getBelt(), updatedMemberFromService.getBelt());
        assertEquals(memberFromService.getMemEmail(), updatedMemberFromService.getMemEmail());
    }


}
