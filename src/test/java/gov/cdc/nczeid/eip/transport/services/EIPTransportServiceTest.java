package gov.cdc.nczeid.eip.transport.services;

import com.google.gson.Gson;
import gov.cdc.nczeid.eip.transport.model.EIPMessage;
import gov.cdc.nczeid.uilt.LoadJson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EIPTransportServiceTest {

    @Autowired
    private EIPTransportService service;

    @Before
    public void setUp() throws Exception {
    }

//    public EIPMessage readJson(String filename) {
//        InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
//        String content = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining());
//        //get JSON:
//        Gson gson = new Gson();
//        return gson.fromJson(content, EIPMessage.class);
//    }

    @Test
    public void acceptMessage() throws Exception {
        //Read from file:
        service.acceptMessage(LoadJson.readJson("simpleMessage.txt"));
    }

    @Test
    public void testMinimumMessage() throws Exception {
        service.acceptMessage(LoadJson.readJson("minimumMessage.txt"));
    }

}