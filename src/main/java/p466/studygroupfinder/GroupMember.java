package p466.studygroupfinder;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("GROUPMEMBER")
public class GroupMember {
    @Id
    private Long id;
    private Long studyGroupId;
    private Long userId;
}