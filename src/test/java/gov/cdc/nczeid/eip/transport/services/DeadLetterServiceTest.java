package gov.cdc.nczeid.eip.transport.services;

import gov.cdc.nczeid.eip.transport.model.DeadLetter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DeadLetterServiceTest {

    @Autowired
    private DeadLetterService service;
    @Test
    public void persistDeadLetter() throws Exception {
        DeadLetter dl = new DeadLetter();
        dl.setDescription("Unit test -" + System.currentTimeMillis());
        dl.setBlob("Invalid payload");
        service.persistDeadLetter(dl);

    }

}