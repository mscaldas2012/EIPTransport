package gov.cdc.nczeid.eip.transport.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.cdc.nczeid.eip.rest.ApiVersion;
import gov.cdc.nczeid.eip.transport.model.DeadLetter;
import gov.cdc.nczeid.eip.transport.model.EIPMessage;
import gov.cdc.nczeid.eip.transport.services.DeadLetterService;
import gov.cdc.nczeid.eip.transport.services.EIPTransportService;
import gov.cdc.nczeid.eip.utils.InputStreamUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/message")
@ApiVersion({1})
public class MessageTransportController {

    private Log log = LogFactory.getLog(MessageTransportController.class);

    @Autowired
    private EIPTransportService transportService;
    @Autowired
    private DeadLetterService deadLetterService;

    @RequestMapping(value = "", method = POST)
    @ResponseBody
    public ResponseEntity sendMessage(@RequestBody EIPMessage content) throws JsonProcessingException {
        try {
            //Make sure content has at least a systemID and a payload:
            if (content.getPayload() == null || content.getPayload().getContent() == null || content.getPayload().getContent().isEmpty() ||
                    content.getSource() == null || content.getSource().getSystemId() == null || content.getSource().getSystemId().isEmpty()) {
                //Returns a 422 - Unprocessable entity.
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Missing Required fields systemID and/or Payload content");
            }
            String guid = transportService.acceptMessage(content);
            log.info("Accepted message " + guid);
            //Returns a 202 - Accepted:
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(guid);
        } catch (Exception e) {
            log.error("exception: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @RequestMapping("/error")
    public ResponseEntity<String> resolveException(HttpServletRequest request) {
        ContentCachingRequestWrapper creq = (ContentCachingRequestWrapper) request;
        String requestBody = InputStreamUtils.getContentAsString(creq.getContentAsByteArray(), 1000, creq.getCharacterEncoding());
        log.error("Error processing request... " + requestBody);
        // --> Save to Dead Letter
        DeadLetter dl = new DeadLetter();
        dl.setBlob(requestBody);
        dl.setDescription(("Invalid Transport Payload"));
        dl.setCallerIP(request.getRemoteAddr());
        deadLetterService.persistDeadLetter(dl);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }
}
