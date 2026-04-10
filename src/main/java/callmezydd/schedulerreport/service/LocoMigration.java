package callmezydd.schedulerreport.service;

import callmezydd.schedulerreport.model.LocoDataMySQL;
import callmezydd.schedulerreport.model.LocoDataMongo;
import callmezydd.schedulerreport.repo.LocoMongoRepository;
import callmezydd.schedulerreport.repo.LocoMySQLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocoMigration {

    private final LocoMongoRepository locoMongoRepository;
    private final LocoMySQLRepository locoMySQLRepository;

    @Autowired
    public LocoMigration(LocoMongoRepository locoMongoRepository, LocoMySQLRepository locoMySQLRepository) {
        this.locoMongoRepository = locoMongoRepository;
        this.locoMySQLRepository = locoMySQLRepository;
    }

    public List<LocoDataMongo> getAllLocoMongoData() {
        return locoMongoRepository.findAll();
    }

    public boolean existsLocoDataMySQLById(String locoCode) {
        return locoMySQLRepository.existsById(locoCode);
    }

    public void saveLocoDataMySQL(LocoDataMySQL locoDataMySQL) {
        locoMySQLRepository.save(locoDataMySQL);
    }

    public void fetchAndSaveData() {
        List<LocoDataMongo> locoMongoList = locoMongoRepository.findAll();
        for (LocoDataMongo locoMongo : locoMongoList) {
            // Check if data with the same ID already exists in MySQL
            if (!locoMySQLRepository.existsById(locoMongo.getLocoCode())) {
                // Log locoMongo data
//                System.out.println("LocoMongo Data: " + locoMongo);

                // Check if dateTime is not null before saving
                if (locoMongo.getTime() != null) {
                    // If not, save the data to MySQL
                    LocoDataMySQL locoDataMySQL = new LocoDataMySQL(
                            locoMongo.getLocoCode(),
                            locoMongo.getLocoName(),
                            locoMongo.getLocoDimension(),
                            locoMongo.getStatus(),
                            locoMongo.getTime()
                    );
                    locoMySQLRepository.save(locoDataMySQL);
                } else {
                    // Log a warning or handle the case where dateTime is null
                    System.out.println("Skipping due to null dateTime for locoCode: " + locoMongo.getLocoCode());
                }
            }
        }
    }
}