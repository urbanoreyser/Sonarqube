package callmezydd.schedulerreport.repo;

import callmezydd.schedulerreport.model.LocoDataMySQLReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocoMySQLReportRepository extends JpaRepository<LocoDataMySQLReport, Integer> {
    LocoDataMySQLReport findTopByOrderByDateTimeDesc();
}
