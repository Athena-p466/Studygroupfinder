package p466.studygroupfinder;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("COURSE")
public class Course {

    @Id
    private final String id;
    private final String title;
    private final String instructor;
    
}
