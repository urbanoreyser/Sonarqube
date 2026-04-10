package callmezydd.schedulerreport.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "loco_report")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocoDataMySQLReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    private int totalLoco;
    private int locoGood;
    private int locoPoor;
    private int locoExcellent;
    private LocalDateTime dateTime;
    private String topLocoAll;
//    @Column(name = "topLoco", length = 20)
//    private String topLoco;


}
