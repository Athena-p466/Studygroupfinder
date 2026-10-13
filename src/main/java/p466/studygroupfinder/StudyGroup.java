package p466.studygroupfinder;

import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Table
public class StudyGroup {

    @Id
    private Long id;
    private String courseId;
    private String title;
    private String description;
    private String location;
    private int capacity;
    private LocalDateTime meetingTime;


}
