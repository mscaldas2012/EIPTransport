package gov.cdc.nczeid.eip.transport.services;

import gov.cdc.nczeid.eip.transport.model.DeadLetter;
import gov.cdc.nczeid.eip.transport.repository.DeadLetterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class DeadLetterService {

    @Autowired
    private DeadLetterRepo repo;

    public void persistDeadLetter(DeadLetter deadLetter) {
        deadLetter.setGuid(UUID.randomUUID().toString());
        deadLetter.setCreatedTime(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
        repo.save(deadLetter);
    }
}
