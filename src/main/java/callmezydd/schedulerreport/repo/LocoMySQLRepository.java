package callmezydd.schedulerreport.repo;

import callmezydd.schedulerreport.model.LocoDataMySQL;
import callmezydd.schedulerreport.model.LocoDataMySQLReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocoMySQLRepository extends JpaRepository<LocoDataMySQL, String> {
    boolean existsById(String locoCode);

    @Query(nativeQuery = true, value = "SELECT DISTINCT locoName FROM (SELECT locoName, COUNT(locoName) as topLoco FROM locomotive_raw GROUP BY locoName ORDER BY topLoco DESC LIMIT 1) AS subquery")
    List<String> findTopLocoNames();

    long countByLocoStatus(String locoStatus);

    List<LocoDataMySQL> findByLocoStatus(String locoStatus);

    @Query("SELECT l FROM LocoDataMySQL l WHERE FUNCTION('YEAR', l.dateTime) = :year AND FUNCTION('MONTH', l.dateTime) = :month AND FUNCTION('DAY', l.dateTime) = :day")
    List<LocoDataMySQL> findByYearAndMonthAndDay(@Param("year") int year, @Param("month") int month, @Param("day") int day);

    LocoDataMySQL findTopByOrderByDateTimeDesc();
}