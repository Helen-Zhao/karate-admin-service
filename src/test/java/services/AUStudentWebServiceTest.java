package services;

import domain.AUStudent;
import domain.Belt;
import domain.Fees;
import domain.Session;
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

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Created by helen on 30/08/2016.
 */
public class AUStudentWebServiceTest {
    private static final String WEB_SERVICE_URI = "http://localhost:8000/students";

    private static final Logger _logger = LoggerFactory.getLogger(AUStudentWebServiceTest.class);

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
    public void addStudent() {

        AUStudent student = new AUStudent(
                "student@aucklanduni.ac.nz",
                Belt.GREEN,
                new ArrayList<>(),
                new Fees(),
                12345,
                "abcd123",
                false
        );

        dto.AUStudent dtoAUStudent = AUStudentMapper.toDto(student);

        Response response = _client.
                target(WEB_SERVICE_URI)
                .request()
                .post(Entity.entity(dtoAUStudent, MediaType.APPLICATION_XML));

        if (response.getStatus() != 201) {
            fail("Failed to create new Member");
        }

        String location = response.getLocation().toString();
        response.close();

        dto.AUStudent dtoAUStudentFromService = _client.target(location)
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(dto.AUStudent.class);

        AUStudent studentFromService = AUStudentMapper.toDomainModel(dtoAUStudentFromService);

        assertEquals(student.getBelt(), studentFromService.getBelt());
        assertEquals(student.getEmail(), studentFromService.getEmail());
        assertEquals(student.getAuid(), studentFromService.getAuid());
        assertEquals(student.getUpi(), studentFromService.getUpi());
        assertEquals(student.isPaidAnnualFee(), studentFromService.isPaidAnnualFee());
    }
//
//    @Test
//    public void updateMember() {
//        Member member = new Member(
//                "booooo@example.com",
//                Belt.YELLOW,
//                25
//        );
//
//        dto.Member dtoMember = MemberMapper.toDto(member);
//
//        Response response = _client.
//                target(WEB_SERVICE_URI)
//                .request()
//                .post(Entity.entity(dtoMember, MediaType.APPLICATION_XML));
//
//        if (response.getStatus() != 201) {
//            fail("Failed to create new Member");
//        }
//
//        String location = response.getLocation().toString();
//        response.close();
//
//        dto.Member dtoMemberFromService = _client.target(location)
//                .request()
//                .accept(MediaType.APPLICATION_XML)
//                .get(dto.Member.class);
//
//        Member memberFromService = MemberMapper.toDomainModel(dtoMemberFromService);
//
//        /**
//         * Updating
//         *
//         */
//
//        memberFromService.setBelt(Belt.YELLOW_TAB);
//        memberFromService.setAttendanceThisYear(memberFromService.getAttendanceThisYear() + 1);
//
//        dtoMember = MemberMapper.toDto(memberFromService);
//        Response response1 = _client.
//                target(WEB_SERVICE_URI + "/" + dtoMember.getId())
//                .request()
//                .put(Entity.entity(dtoMember, MediaType.APPLICATION_XML));
//
//        if (response1.getStatus() != 204) {
//            fail("Failed to update Member");
//        }
//
//        response.close();
//
//
//
//        dto.Member updatedDtoMemberFromService = _client.target(WEB_SERVICE_URI + "/" + memberFromService.getId())
//                .request()
//                .accept(MediaType.APPLICATION_XML)
//                .get(dto.Member.class);
//
//        Member updatedMemberFromService = MemberMapper.toDomainModel(updatedDtoMemberFromService);
//
//
//        assertEquals(memberFromService.getAttendanceThisYear(), updatedMemberFromService.getAttendanceThisYear());
//        assertEquals(memberFromService.getBelt(), updatedMemberFromService.getBelt());
//        assertEquals(memberFromService.getMemEmail(), updatedMemberFromService.getMemEmail());
//    }
//
//    @Test
//    public void getAllMembers() {
//        GenericType<List<dto.Member>> list = new GenericType<List<dto.Member>>() {};
//        List<dto.Member> members = _client.target(WEB_SERVICE_URI)
//                .request()
//                .accept(MediaType.APPLICATION_XML)
//                .get(list);
//        System.out.println(members.size());
//    }


}
