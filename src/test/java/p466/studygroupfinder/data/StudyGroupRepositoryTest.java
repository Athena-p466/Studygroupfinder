package p466.studygroupfinder.data;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import p466.studygroupfinder.Course;
import p466.studygroupfinder.StudyGroup;

import java.util.List;

@SpringBootTest
public class StudyGroupRepositoryTest {

    @Autowired
    private StudyGroupRepository groupRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Test
    public void shouldFindStudyGroups(){
        List<StudyGroup> groups = groupRepo.findAll();
        assertThat(groups).isNotEmpty();
    }

    @Test
    public void shouldFindGroupsByCourse() {
        List<StudyGroup> groups = groupRepo.findByCourseId("CSCI-P466");
        assertThat(groups).isNotEmpty();
        assertThat(groups).allMatch(g -> g.getCourseId().equals("CSCI-P466"));
    }

    @Test
    public void shouldFindCourseById(){
        Course course = courseRepo.findById("CSCI-P466").orElseThrow();
        assertThat(course.getTitle()).isEqualTo("Software Engineering");
    }


}
