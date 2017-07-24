package gov.cdc.nczeid.eip.transport.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "transport_eip_messages")
public class EIPMessage {
    private Metadata metadata = new Metadata();
    private Source source;
    private Payload payload;


    //persistence only - not on JSON:
    @Id
    private String id;

    private String tguid;
    private String status;
    private String receivedTime;

}
