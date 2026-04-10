package callmezydd.schedulerreport.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "locomotive_raw")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocoDataMySQL {
    @Id
    @Column(name = "locoCode")
    private String locoCode;

    @Column(name = "locoName")
    private String locoName;
    @Column(name = "locoDimension")
    private String locoDimension;
    @Column(name = "locoStatus")
    private String locoStatus;
    @Column(name = "dateTime")
    private LocalDateTime dateTime;
}
