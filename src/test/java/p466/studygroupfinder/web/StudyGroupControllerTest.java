package p466.studygroupfinder.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import p466.studygroupfinder.StudyGroup;
import p466.studygroupfinder.data.CourseRepository;
import p466.studygroupfinder.data.StudyGroupRepository;

@WebMvcTest(StudyGroupController.class)
public class StudyGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudyGroupRepository groupRepo;

    @MockitoBean
    private CourseRepository courseRepo;

    @Test
    public void testShowAllGroups() throws Exception{
        org.mockito.Mockito.when(courseRepo.findAll()).thenReturn(Collections.emptyList());
        org.mockito.Mockito.when(groupRepo.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/groups")).andExpect(status().isOk());
    }

}
