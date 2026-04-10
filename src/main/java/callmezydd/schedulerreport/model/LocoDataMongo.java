package callmezydd.schedulerreport.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "locomotifs")
public class LocoDataMongo {
    @Id
    private String _id;

    private String locoCode;
    private String locoName;
    private String locoDimension;
    private String status;
    private LocalDateTime time;
}
