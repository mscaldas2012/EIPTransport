package gov.cdc.nczeid.eip.transport.repository;

import gov.cdc.nczeid.eip.transport.model.DeadLetter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeadLetterRepo extends MongoRepository<DeadLetter, String> {
}
