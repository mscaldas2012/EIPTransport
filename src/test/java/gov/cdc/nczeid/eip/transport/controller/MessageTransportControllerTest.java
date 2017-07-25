package gov.cdc.nczeid.eip.transport.controller;

import gov.cdc.nczeid.eip.transport.model.EIPMessage;
import gov.cdc.nczeid.uilt.LoadJson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.yml")
public class MessageTransportControllerTest {


    public static final String APPLICATION_JSON = "application/json";
    @Value("serverRootURL")
    private String serverURL;

    private String rootAPIIURL;
    private String messageEndpoint;

    @Before
    public void setUp() throws Exception {
        this.rootAPIIURL = "http://localhost:8081/v1/";
        this.messageEndpoint = rootAPIIURL + "message";

    }
    private void saveMessage(String fileName) {
        EIPMessage msg = LoadJson.readJson(fileName);
        given()
                .contentType(APPLICATION_JSON)
                .body(msg)
                .when()
                .post(this.messageEndpoint)
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
                .contentType(APPLICATION_JSON)
                .body(badMessage)
                .when()
                .post(this.messageEndpoint)
                .then()
                .statusCode(400);

    }

    @Test
    public void testPayloadNoContent() {
        String msgNoContent = LoadJson.readFile("messageNoContent.txt");
        given()
                .contentType(APPLICATION_JSON)
                .body(msgNoContent)
                .when()
                .post(this.messageEndpoint)
                .then()
                .statusCode(422);
    }

    @Test
    public void testPayloadNoSystemId() {
        String msgNoContent = LoadJson.readFile("messageNoSystemID.txt");
        given()
                .contentType(APPLICATION_JSON)
                .body(msgNoContent)
                .when()
                .post(this.messageEndpoint)
                .then()
                .statusCode(422);

    }

    @Test
    public void testNoPayload() {
        String msgNoContent = LoadJson.readFile("messageNoPayload.txt");
        given()
                .contentType(APPLICATION_JSON)
                .body(msgNoContent)
                .when()
                .post(this.messageEndpoint)
                .then()
                .statusCode(422);
    }

}