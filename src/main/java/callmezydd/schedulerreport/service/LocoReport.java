package callmezydd.schedulerreport.service;

import callmezydd.schedulerreport.model.LocoDataMySQLReport;
import callmezydd.schedulerreport.repo.LocoMySQLReportRepository;
import callmezydd.schedulerreport.repo.LocoMySQLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LocoReport {

    private final LocoMySQLRepository locoMySQLRepository;
    private final LocoMySQLReportRepository locoMySQLReportRepository;

    @Autowired
    public LocoReport(LocoMySQLRepository locoMySQLRepository, LocoMySQLReportRepository locoMySQLReportRepository) {
        this.locoMySQLRepository = locoMySQLRepository;
        this.locoMySQLReportRepository = locoMySQLReportRepository;
    }

    @Scheduled(fixedRate = 600000) // Run every 10 minutes
    public void generateAndSaveReport() {
        try {
            // Retrieve the latest dateTime from loco_report
            LocalDateTime latestReportDateTime = locoMySQLReportRepository.findTopByOrderByDateTimeDesc().getDateTime();

            // Retrieve the latest dateTime from loco_raw
            LocalDateTime latestRawDateTime = locoMySQLRepository.findTopByOrderByDateTimeDesc().getDateTime();

            // Check if there is new data in loco_raw
            if (latestRawDateTime.isAfter(latestReportDateTime)) {
                // Create LocoReport instance
                LocoDataMySQLReport locoReport = createLocoReport();

                // Save the report to the new table
                locoMySQLReportRepository.save(locoReport);

                // Logging to see if the report is generated and saved
                System.out.println("Report generated and saved: " + locoReport);
            } else {
                // Log: No new data found in loco_raw
                System.out.println("No new data found in loco_raw.");
            }
        } catch (Exception e) {
            // Log: Exception details
            e.printStackTrace();
        }
    }

    private LocoDataMySQLReport createLocoReport() {
        long totalLoco = locoMySQLRepository.count();
        long totalLocoGood = locoMySQLRepository.countByLocoStatus("Good");
        long totalLocoPoor = locoMySQLRepository.countByLocoStatus("Poor");
        long totalLocoExcellent = locoMySQLRepository.countByLocoStatus("Excellent");
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Calculate topLoco (loco terbanyak yang muncul)
        String topLocoOverall = findTopLocoOverall();

        // Create LocoReport instance
        LocoDataMySQLReport locoReport = new LocoDataMySQLReport();
        locoReport.setTotalLoco((int) totalLoco);
        locoReport.setTopLocoAll(topLocoOverall);
        locoReport.setLocoGood((int) totalLocoGood);
        locoReport.setLocoPoor((int) totalLocoPoor);
        locoReport.setLocoExcellent((int) totalLocoExcellent);
        locoReport.setDateTime(currentDateTime);

        return locoReport;
    }

    private String findTopLocoOverall() {
        // Use the query to get a list of loco names ordered by overall frequency
        List<String> topLocoNamesOverall = locoMySQLRepository.findTopLocoNames();

        // Check if the list is not empty
        if (!topLocoNamesOverall.isEmpty()) {
            // Choose the first element as the top loco
            String topLoco = topLocoNamesOverall.get(0);
            // System.out.println("Length of topLoco: " + topLoco.length());

            // Trim the data to fit within the specified length (e.g., 10)
            return topLoco.length() > 5 ? topLoco.substring(0, 5) : topLoco;
        } else {
            // Handle the case where there are no loco names overall
            return "No top loco found overall";
        }
    }
}
