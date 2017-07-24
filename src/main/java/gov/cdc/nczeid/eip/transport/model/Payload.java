package gov.cdc.nczeid.eip.transport.model;

import lombok.Data;



@Data
public class Payload {
    private String content;
    private ContentEncodings encoding = ContentEncodings.BASE64;
    private int contentLength;
    private String hash;

    private boolean multipart ;
    private int multipartIndex;
    private String multipartId;
    private int multipartTotal;
}
