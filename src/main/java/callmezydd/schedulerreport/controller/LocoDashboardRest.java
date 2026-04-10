package callmezydd.schedulerreport.controller;

import callmezydd.schedulerreport.model.LocoDataMySQL;
import callmezydd.schedulerreport.repo.LocoMySQLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class LocoDashboardRest {
    final LocoMySQLRepository locoMySQLRepository;

    @Autowired
    public LocoDashboardRest(LocoMySQLRepository locoMySQLRepository){
        this.locoMySQLRepository = locoMySQLRepository;
    }

    //get all data
    @GetMapping("/getDataRaw")
    public List<LocoDataMySQL> getDataLoco(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month,
            @RequestParam(name = "day", required = false) Integer day){

        if (year != null && month != null && day != null) {
            // Jika ada parameter tahun, bulan, dan tanggal
            return locoMySQLRepository.findByYearAndMonthAndDay(year, month, day);
        } else if (status != null && !status.isEmpty()) {
            // Jika ada parameter status
            return locoMySQLRepository.findByLocoStatus(status);
        } else {
            // Jika tidak ada parameter khusus, ambil semua data
            return locoMySQLRepository.findAll();
        }
    }
}
