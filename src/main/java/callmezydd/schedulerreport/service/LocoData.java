package callmezydd.schedulerreport.service;

import callmezydd.schedulerreport.model.LocoDataMySQL;
import callmezydd.schedulerreport.repo.LocoMySQLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocoData {
    final LocoMySQLRepository locoMySQLRepository;

    @Autowired
    public LocoData( LocoMySQLRepository locoMySQLRepository){
        this.locoMySQLRepository = locoMySQLRepository;
    }

    public List<LocoDataMySQL> findAll(){
        return locoMySQLRepository.findAll();
    }


}
