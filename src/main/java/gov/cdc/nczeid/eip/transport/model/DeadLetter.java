package gov.cdc.nczeid.eip.transport.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "dead_letters")
public class DeadLetter {

    @Id
    private String id;

    private String guid;
    private String blob;
    private String description;
    private String createdTime;

    private String callerIP;

}
