package gov.cdc.nczeid.eip.transport.model;

import lombok.Data;
import java.util.List;


@Data
public class Source {
    private String system;
    private String systemLocation;
    private String systemId;

    private String messageIdentifier;
    private String receivedTime;
    private String sender;
    private String recipient;

    private Source receivedFrom;

    private List sourceAttributes;



}
