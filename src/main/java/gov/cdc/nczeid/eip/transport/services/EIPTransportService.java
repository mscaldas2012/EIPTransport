package gov.cdc.nczeid.eip.transport.services;

import gov.cdc.nczeid.eip.transport.model.EIPMessage;
import gov.cdc.nczeid.eip.transport.model.Payload;
import gov.cdc.nczeid.eip.transport.repository.EIPTransportMongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class EIPTransportService {

    @Autowired
    private EIPTransportMongoRepo repo;


    public String acceptMessage(EIPMessage message) {
        message.setTguid(UUID.randomUUID().toString());
        message.setReceivedTime(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
        message.setStatus("ACCEPTED");
        //Set content Length and Hash:
        Payload payload = message.getPayload();
        if (payload != null ) {
            //TODO::Need to decode probably...
            if (payload.getContentLength() <= 0) {
                payload.setContentLength(message.getPayload().getContent().length());
            }
            if (payload.getHash() == null || payload.getHash().isEmpty()) {
                //TODO::User a proper hashing...
                payload.setHash(payload.getContent().hashCode() + "");
            }
        }
        EIPMessage msg =  repo.save(message);
        return msg.getTguid();
    }
}
