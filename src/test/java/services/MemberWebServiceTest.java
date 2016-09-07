package services;

import domain.Member;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.attribute.standard.Media;
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
    private static final String WEB_SERVICE_URI = "http://localhost:10000/members";

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
                "helloworld@example.com",
                Member.Belt.BLACK_FIRST_DAN,
                100
        );

        dto.Member dtoMember = MemberMapper.toDto(member);

        Response response = _client.
                target(WEB_SERVICE_URI)
                .request()
                .post(Entity.xml(dtoMember));

        if (response.getStatus() != 201) {
            System.out.println("" + response.getStatus());
            System.out.println(response.getMediaType());
            System.out.println(response.getStatusInfo());
            fail("Failed to create new Parolee");
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

}
