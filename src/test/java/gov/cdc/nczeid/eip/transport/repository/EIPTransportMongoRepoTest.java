package gov.cdc.nczeid.eip.transport.repository;

import com.google.gson.Gson;
import gov.cdc.nczeid.eip.transport.model.EIPMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EIPTransportMongoRepoTest {

    @Autowired
    private EIPTransportMongoRepo repo;

    //@Test
    public void save() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("simpleMessageBasic.txt");

        String content = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining());
        //get JSON:
        Gson gson = new Gson();
        EIPMessage message = gson.fromJson(content, EIPMessage.class);
        repo.save(message);
    }

}