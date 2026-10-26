package p466.studygroupfinder.data;

import org.springframework.data.repository.CrudRepository;
import p466.studygroupfinder.GroupMember;

public interface GroupMemberRepository extends CrudRepository<GroupMember, Long> {
    boolean existsByStudyGroupIdAndUserId(Long studyGroupId, Long userId);
    long countByStudyGroupId(Long studyGroupId);
}