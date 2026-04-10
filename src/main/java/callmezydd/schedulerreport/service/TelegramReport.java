package callmezydd.schedulerreport.service;

import callmezydd.schedulerreport.config.TelegramBot;
import callmezydd.schedulerreport.model.LocoDataMySQLReport;
import callmezydd.schedulerreport.repo.LocoMySQLReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TelegramReport {

    private final TelegramBot telegramBot;
    private final LocoMySQLReportRepository locoMySQLReportRepository;

    @Value("${telegram.bot.chatId}")
    private String chatId;

    private Long lastSentReportId = 0L; // ID terakhir yang dikirim

    @Autowired
    public TelegramReport(TelegramBot telegramBot, LocoMySQLReportRepository locoMySQLReportRepository) {
        this.telegramBot = telegramBot;
        this.locoMySQLReportRepository = locoMySQLReportRepository;
    }

    @Scheduled(fixedRate = 600000) // Run every 10 minutes
    public void sendReportToTelegram() {
        try {
            // Log: Start sending report
            System.out.println("Sending report to Telegram...");

            // Retrieve the latest report from the database
            LocoDataMySQLReport latestReport = locoMySQLReportRepository.findTopByOrderByDateTimeDesc();

            // Log: Check if the latest report is retrieved successfully
            System.out.println("Latest Report: " + latestReport);

            // Check if there is a report and the ID is greater than the last sent report ID
            if (latestReport != null && latestReport.getId() > lastSentReportId) {
                // Count the total number of reports
                long totalReports = locoMySQLReportRepository.count();

                // Check if the total number of reports has changed
                if (totalReports != lastSentReportId) {
                    // Convert the report to a formatted string
                    String reportText = formatReport(latestReport);

                    // Log: Report text before sending
                    System.out.println("Report Text: \n" + reportText);

                    // Send the report to Telegram
                    telegramBot.sendMessage(chatId, reportText);

                    // Update the last sent report ID
                    lastSentReportId = totalReports;

                    // Log: Report sent successfully
                    System.out.println("Report sent successfully.");
                } else {
                    // Log: Data is outdated
                    String outdatedMessage =
                            "Summary Report is Outdated" +
                            "\n========================="+
                            "\nLatest report is " +
                            latestReport.getDateTime() +
                            "\n=========================";
                    System.out.println(outdatedMessage);

                    // Send outdated message to Telegram
                    telegramBot.sendMessage(chatId, outdatedMessage);
                }
            } else {
                // Log: Data is outdated
                String outdatedMessage = "Data Outdated: " + latestReport.getDateTime();
                System.out.println(outdatedMessage);

                // Send outdated message to Telegram
                telegramBot.sendMessage(chatId, outdatedMessage);
            }
        } catch (Exception e) {
            // Log: Exception details
            e.printStackTrace();
        }
    }

    public String formatReport(LocoDataMySQLReport report) {
        // Format the report data into a string
        // Modify this according to your report structure
        return  "Latest Summray Loco" +
                "\n=========================" +
                "\nTotal Loco\t: " + report.getTotalLoco() +
                "\nTop Loco\t: " + report.getTopLocoAll() +
                "\nLoco Good\t: " + report.getLocoGood() +
                "\nLoco Poor\t: " + report.getLocoPoor() +
                "\nExcellent\t: " + report.getLocoExcellent() +
                "\nDateTime\t: " + report.getDateTime()+
                "\n=========================" +
                "\nnb: report will auto update every 10 minutes.";
    }
}
