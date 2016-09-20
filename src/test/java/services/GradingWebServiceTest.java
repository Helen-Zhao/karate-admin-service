package services;

import domain.*;
import org.apache.commons.lang3.time.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by helen on 15/09/2016.
 */
public class GradingWebServiceTest {
    private static final String WEB_SERVICE_URI = "http://localhost:8000/service/gradings";

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
    public void testCreateGradingCascadeMember() {
        Date today = DateUtils.ceiling(DateUtils.addDays(new Date(), 3), Calendar.DAY_OF_MONTH);
        Grading grading = new Grading(today);
        Member member = new Member(
                "atagrading@sldfkndfklsnfesk",
                Belt.BROWN_ONE_TAB
        );
        grading.getAttendees().add(member);
        Response response = _client.
                target(WEB_SERVICE_URI)
                .request()
                .post(Entity.entity(grading, MediaType.APPLICATION_XML));

        if (response.getStatus() != 201) {
            fail("Failed to create new Grading");
        }

        String location = response.getLocation().toString();
        _logger.info("location:" + location);
        response.close();

        Grading gradingFromService = _client.target(location)
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(Grading.class);

        assertEquals(grading.getDate(), gradingFromService.getDate());
        assertTrue(gradingFromService != null && gradingFromService.getAttendees().size() > 0);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
        Grading newGrading = _client.
                target(WEB_SERVICE_URI + "/" + sdf.format(today))
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(Grading.class);

        newGrading.getAttendees().add(
                new AUStudent(
                        "eagerstudent@aucklanduni.ac.nz",
                        Belt.YELLOW_TAB,
                        2234323,
                        "ddd333",
                        false
                ));

        //update grading
        Response response1 = _client
                .target(WEB_SERVICE_URI + "/" + sdf.format(today))
                .request()
                .put(Entity.entity(newGrading, MediaType.APPLICATION_XML));

        if  (response1.getStatus() != 204) {
            fail("Failed to update grading");
        }

        Grading updatedGrading = _client.
                target(WEB_SERVICE_URI + "/" + sdf.format(today))
                .request()
                .accept(MediaType.APPLICATION_XML)
                .cookie(new NewCookie("cache", "ignore-cache"))
                .get(Grading.class);

        assertEquals(newGrading.getDate(), updatedGrading.getDate());
        assertTrue(updatedGrading != null && updatedGrading.getAttendees().size() > 0);

    }
}
