package gov.cdc.nczeid.eip.transport.repository;

import gov.cdc.nczeid.eip.transport.model.EIPMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EIPTransportMongoRepo extends MongoRepository<EIPMessage, String> {
    public EIPMessage findByTguid(String tguid);
}
