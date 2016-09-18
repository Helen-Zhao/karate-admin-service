package services;

import domain.AUStudent;
import domain.Belt;
import domain.Fees;
import dto.Member;
import org.jboss.resteasy.util.GenericType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.members.AUStudentMapper;
import services.members.MemberMapper;

import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Created by helen on 30/08/2016.
 */
public class AUStudentWebServiceTest {
    private static final String WEB_SERVICE_URI = "http://localhost:8000/service/students";

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

    @Test
    public void updateStudent() {
        AUStudent student = new AUStudent(
                "cooleststudent@lslalala.ss",
                Belt.GREEN_TAB,
                new Fees(12.9d),
                1234124,
                "hzhsa2882",
                true

        );

        dto.AUStudent dtoStudent= AUStudentMapper.toDto(student);

        Response response = _client.
                target(WEB_SERVICE_URI)
                .request()
                .post(Entity.entity(dtoStudent, MediaType.APPLICATION_XML));

        if (response.getStatus() != 201) {
            fail("Failed to create new Member");
        }

        String location = response.getLocation().toString();
        response.close();

        dto.AUStudent dtoStudentFromService = _client.target(location)
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(dto.AUStudent.class);

        AUStudent studentFromService = AUStudentMapper.toDomainModel(dtoStudentFromService);

        /**
         * Updating
         *
         */

        studentFromService.setBelt(Belt.BROWN);

        dtoStudent = AUStudentMapper.toDto(studentFromService);
        Response response1 = _client.
                target(WEB_SERVICE_URI + "/" + dtoStudent.getId())
                .request()
                .put(Entity.entity(dtoStudent, MediaType.APPLICATION_XML));

        if (response1.getStatus() != 204) {
            fail("Failed to update student");
        }

        response.close();

        dto.AUStudent updatedDtostudentFromService = _client.target(WEB_SERVICE_URI + "/" + studentFromService.getId())
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(dto.AUStudent.class);

        AUStudent updatedStudentFromService = AUStudentMapper.toDomainModel(updatedDtostudentFromService);


        assertEquals(studentFromService.getBelt(), updatedStudentFromService.getBelt());
        assertEquals(studentFromService.getEmail(), updatedStudentFromService.getEmail());
    }

    @Test
    public void getAllMembers() {
        javax.ws.rs.core.GenericType<List<dto.AUStudent>> list = new javax.ws.rs.core.GenericType<List<dto.AUStudent>>() {};
        List<dto.AUStudent> students = _client.target(WEB_SERVICE_URI)
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(list);

        _logger.info("" + students.size());
        assertTrue(students != null && students.size() > 0);
    }

}
