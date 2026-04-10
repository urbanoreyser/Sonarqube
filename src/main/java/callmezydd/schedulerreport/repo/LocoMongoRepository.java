package callmezydd.schedulerreport.repo;

import callmezydd.schedulerreport.model.LocoDataMongo;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface LocoMongoRepository extends MongoRepository<LocoDataMongo, String> {
}