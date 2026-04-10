package callmezydd.schedulerreport.service;

import callmezydd.schedulerreport.config.TelegramBot;
import callmezydd.schedulerreport.model.LocoDataMySQL;
import callmezydd.schedulerreport.model.LocoDataMySQLReport;
import callmezydd.schedulerreport.repo.LocoMySQLReportRepository;
import callmezydd.schedulerreport.repo.LocoMySQLRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


//@ExtendWith(SpringExtension.class)
//@SpringBootTest
@TestPropertySource(properties = {"telegram.bot.chatId=yourTestChatId"})
class TelegramReportTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private LocoMySQLReportRepository locoMySQLReportRepository;

    @Mock
    private LocoMySQLRepository locoMySQLRepository;

    @Value("${telegram.bot.chatId}")
    private String chatId;

    private TelegramReport telegramReport;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        telegramReport = new TelegramReport(telegramBot, locoMySQLReportRepository);
    }

    @Test
    void testSendReportToTelegram_ExceptionHandling() {
        // Mock behavior of repositories to throw an exception
        when(locoMySQLReportRepository.findTopByOrderByDateTimeDesc()).thenThrow(new RuntimeException("Simulated exception"));

        // Call the method to be tested
        telegramReport.sendReportToTelegram();

        // Verify that the sendMessage method was not called
        verify(telegramBot, times(0)).sendMessage(eq(chatId), anyString());
    }

    @Test
    void testSendReportToTelegram_IDNotGreaterThanLastSentReportId() {
        // Mock data where latestReportDateTime is after latestRawDateTime
        LocalDateTime latestReportDateTime = LocalDateTime.of(2023, 1, 1, 1, 0);
        LocalDateTime latestRawDateTime = LocalDateTime.of(2023, 1, 1, 0, 0);

        // Mock LocoDataMySQL and LocoDataMySQLReport
        LocoDataMySQL mockLocoData = createMockLocoData(latestRawDateTime);
        LocoDataMySQLReport mockLocoDataMySQLReport = createMockLocoDataMySQLReport(latestReportDateTime);
        mockLocoDataMySQLReport.setId(1);

        // Mock behavior of repositories
        when(locoMySQLReportRepository.findTopByOrderByDateTimeDesc()).thenReturn(mockLocoDataMySQLReport);
        when(locoMySQLRepository.findTopByOrderByDateTimeDesc()).thenReturn(mockLocoData);
        when(locoMySQLReportRepository.count()).thenReturn(1L); // set totalReports equal to lastSentReportId

        // Call the method to be tested
        telegramReport.sendReportToTelegram();

        // Verify that the sendMessage method was not called
        verify(telegramBot, times(0)).sendMessage(eq("yourTestChatId"), anyString());
    }

    @Test
    void testSendReportToTelegram_NoNewData() {
        // Mock data where latestReportDateTime is not after latestRawDateTime
        LocalDateTime latestReportDateTime = LocalDateTime.of(2023, 1, 1, 1, 0);
        LocalDateTime latestRawDateTime = LocalDateTime.of(2023, 1, 1, 0, 0);

        LocoDataMySQLReport mockLocoDataMySQLReport = createMockLocoDataMySQLReport(latestReportDateTime);

        when(locoMySQLReportRepository.findTopByOrderByDateTimeDesc()).thenReturn(mockLocoDataMySQLReport);
        when(locoMySQLRepository.findTopByOrderByDateTimeDesc()).thenReturn(createMockLocoData(latestRawDateTime));

        // Call the method to be tested
        telegramReport.sendReportToTelegram();

        // Verify that the sendMessage method was not called
        verify(telegramBot, never()).sendMessage(eq(chatId), anyString());
    }

    @Test
    void testSendReportToTelegram_NewDataAvailable() {

        // Mock data where latestReportDateTime is after latestRawDateTime
        LocalDateTime latestReportDateTime = LocalDateTime.of(2023, 1, 1, 1, 0);
        LocalDateTime latestRawDateTime = LocalDateTime.of(2023, 1, 1, 0, 0);

        // Mock LocoDataMySQL and LocoDataMySQLReport
        LocoDataMySQL mockLocoData = createMockLocoData(latestRawDateTime);
        LocoDataMySQLReport mockLocoDataMySQLReport = createMockLocoDataMySQLReport(latestReportDateTime);
        mockLocoDataMySQLReport.setId(1); // Atur id di sini

        // Mock behavior of repositories
        when(locoMySQLReportRepository.findTopByOrderByDateTimeDesc()).thenReturn(mockLocoDataMySQLReport);
        when(locoMySQLRepository.findTopByOrderByDateTimeDesc()).thenReturn(mockLocoData);

        // Call the method to be tested
        telegramReport.sendReportToTelegram();

        // Verify that the sendMessage method was called
        verify(telegramBot, times(1)).sendMessage(eq(chatId), anyString());
    }

    @Test
    void testSendReportToTelegram_LatestReportIsNull() {
        // Mock behavior of repositories
        when(locoMySQLReportRepository.findTopByOrderByDateTimeDesc()).thenReturn(null);

        // Call the method to be tested
        telegramReport.sendReportToTelegram();

        // Verify that the sendMessage method was not called
        verify(telegramBot, times(0)).sendMessage(eq(chatId), anyString());
    }

    @Test
    void testFormatReport() {
        // Arrange
        LocoDataMySQLReport mockReport = new LocoDataMySQLReport();
        mockReport.setTotalLoco(10);
        mockReport.setTopLocoAll("Loco1");
        mockReport.setLocoGood(5);
        mockReport.setLocoPoor(3);
        mockReport.setLocoExcellent(2);
        mockReport.setDateTime(LocalDateTime.of(2023, 1, 1, 1, 0));

        // Act
        String reportText = telegramReport.formatReport(mockReport);

        // Assert
        String expectedReportText = "Latest Summray Loco" +
                "\n=========================" +
                "\nTotal Loco\t: " + 10 +
                "\nTop Loco\t: " + "Loco1" +
                "\nLoco Good\t: " + 5 +
                "\nLoco Poor\t: " + 3 +
                "\nExcellent\t: " + 2 +
                "\nDateTime\t: " + LocalDateTime.of(2023, 1, 1, 1, 0) +
                "\n=========================" +
                "\nnb: report will auto update every 10 minutes.";
        assertEquals(expectedReportText, reportText);
    }

    private LocoDataMySQL createMockLocoData(LocalDateTime dateTime) {
        LocoDataMySQL mockLocoData = new LocoDataMySQL();
        // set properties according to your needs
        mockLocoData.setDateTime(dateTime);
        // ...

        return mockLocoData;
    }

    private LocoDataMySQLReport createMockLocoDataMySQLReport(LocalDateTime dateTime) {
        LocoDataMySQLReport mockLocoDataMySQLReport = new LocoDataMySQLReport();
        // set properties according to your needs
        mockLocoDataMySQLReport.setDateTime(dateTime);

        return mockLocoDataMySQLReport;
    }
}
