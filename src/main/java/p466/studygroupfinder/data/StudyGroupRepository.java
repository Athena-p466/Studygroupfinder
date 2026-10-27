package p466.studygroupfinder.data;

import org.springframework.data.repository.CrudRepository;
import p466.studygroupfinder.StudyGroup;

import java.util.List;

public interface StudyGroupRepository extends CrudRepository<StudyGroup, Long>{
    List<StudyGroup> findAll();
    List<StudyGroup> findByCourseId(String courseId);
    List<StudyGroup> findByOrganizerId(Long organizerId);
}
