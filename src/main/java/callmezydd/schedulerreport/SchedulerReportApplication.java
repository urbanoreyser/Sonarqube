package callmezydd.schedulerreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
@ComponentScan(basePackages = "callmezydd.schedulerreport")
public class SchedulerReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerReportApplication.class, args);
    }

}
