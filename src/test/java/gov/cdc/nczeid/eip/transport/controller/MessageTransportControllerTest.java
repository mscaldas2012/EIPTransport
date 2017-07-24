package gov.cdc.nczeid.eip.transport.controller;

import gov.cdc.nczeid.eip.transport.model.EIPMessage;
import gov.cdc.nczeid.uilt.LoadJson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application.yml")
public class MessageTransportControllerTest {

    private String rootAPIIURL;

    @Before
    public void setUp() throws Exception {
        this.rootAPIIURL = "/v1/";

    }
    private void saveMessage(String fileName) {
        EIPMessage msg = LoadJson.readJson(fileName);
        given()
                .contentType("application/json")
                .body(msg)
                .when()
                .post(this.rootAPIIURL + "message")
                .then()
                .statusCode(202);
    }

    @Test
    public void testSaveSimpleMessage() throws Exception {
        saveMessage("simpleMessage.txt");
    }


    @Test
    public void testSaveMinimumMessage() {
        saveMessage("minimumMessage.txt");
    }

    @Test
    public void testSaveDeadLetter() {
        String badMessage = LoadJson.readFile("invalidMessageDeadLetter.txt");
        given()
                .contentType("application/json")
                .body(badMessage)
                .when()
                .post(this.rootAPIIURL + "message")
                .then()
                .statusCode(400);

    }

    @Test
    public void testPayloadNoContent() {

    }

    @Test
    public void testPayloadNoSystemId() {

    }

    @Test
    public void testNoPayload() {

    }

}