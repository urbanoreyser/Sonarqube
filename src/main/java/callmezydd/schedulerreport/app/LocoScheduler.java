package callmezydd.schedulerreport.app;

import callmezydd.schedulerreport.model.LocoDataMySQL;
import callmezydd.schedulerreport.model.LocoDataMongo;
import callmezydd.schedulerreport.service.LocoMigration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocoScheduler {
    final LocoMigration locoMigration;

    @Autowired
    public LocoScheduler(LocoMigration locoMigration){
        this.locoMigration = locoMigration;
    }

    @Scheduled(fixedRate = 60000) // Run every 1 minute
    public void fetchAndSaveData() {
        List<LocoDataMongo> locoMongoList = locoMigration.getAllLocoMongoData();

        for (LocoDataMongo locoMongo : locoMongoList) {

//            System.out.println("LocoMongo Data: " + locoMongo);
            String locoCode = locoMongo.getLocoCode();

            // Logging to see the locoCode value
//            System.out.println("LocoCode: " + locoCode);

            // Check if data with the same code already exists in MySQL
            if (locoCode != null && !locoMigration.existsLocoDataMySQLById(locoCode)) {
                // If not, save the data to MySQL
                LocoDataMySQL locoDataMySQL = new LocoDataMySQL(
                        locoCode,
                        locoMongo.getLocoName(),
                        locoMongo.getLocoDimension(),
                        locoMongo.getStatus(),
                        locoMongo.getTime()
                );
                locoMigration.saveLocoDataMySQL(locoDataMySQL);

                // Logging to see if data is saved to MySQL
                System.out.println("Saved to MySQL: " + locoCode);
            } else {
//                System.out.println("Skipping due to existing locoCode in MySQL: " + locoCode);
            }
        }
    }
}