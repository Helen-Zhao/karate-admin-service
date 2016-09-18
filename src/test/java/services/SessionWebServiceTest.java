package services;

import domain.AUStudent;
import domain.Belt;
import domain.Member;
import domain.Session;
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
import static org.junit.Assert.fail;

/**
 * Created by helen on 15/09/2016.
 */
public class SessionWebServiceTest {
    private static final String WEB_SERVICE_URI = "http://localhost:8000/service/sessions";

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
    public void testCreateSessionCascadeMember() {

        Session session = new Session();
        Member member = new Member(
                "hello@auckland",
                Belt.WHITE
        );
        session.getAttendees().add(member);
        Response response = _client.
                target(WEB_SERVICE_URI)
                .request()
                .post(javax.ws.rs.client.Entity.entity(session, MediaType.APPLICATION_XML));

        if (response.getStatus() != 201) {
            fail("Failed to create new Session");
        }

        String location = response.getLocation().toString();
        response.close();

        Session sessionFromService = _client.target(location)
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(Session.class);

        assertEquals(session.getDate(), sessionFromService.getDate());
        assertEquals(session.getAttendees().get(0).getEmail(), sessionFromService.getAttendees().get(0).getEmail());
    }

    @Test
    public void testGetSessionCascadeStudent() {

        Date today = DateUtils.round(new Date(), Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
        Session session = _client.
                target(WEB_SERVICE_URI + "/" + sdf.format(today))
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(Session.class);

        session.getAttendees().add(
                new AUStudent(
                        "somestudent@uoa.hi",
                        Belt.WHITE,
                        32324,
                        "jssj333",
                        false
        ));

        //update session
        Response response = _client
                .target(WEB_SERVICE_URI + "/" + sdf.format(today))
                .request()
                .put(Entity.entity(session, MediaType.APPLICATION_XML));

        if  (response.getStatus() != 204) {
            fail("Failed to update session");
        }

        Session updatedSession = _client.
                target(WEB_SERVICE_URI + "/" + sdf.format(today))
                .request()
                .accept(MediaType.APPLICATION_XML)
                .cookie(new NewCookie("cache", "ignore-cache"))
                .get(Session.class);

        assertEquals(session.getDate(), updatedSession.getDate());
        assertEquals(session.getAttendees().get(0).getId(), updatedSession.getAttendees().get(0).getId());
    }







}
