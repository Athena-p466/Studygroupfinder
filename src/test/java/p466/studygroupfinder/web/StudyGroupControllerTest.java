package p466.studygroupfinder.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import p466.studygroupfinder.data.CourseRepository;
import p466.studygroupfinder.data.GroupMemberRepository;
import p466.studygroupfinder.data.StudyGroupRepository;
import p466.studygroupfinder.data.UserRepository;
import p466.studygroupfinder.security.SecurityConfig;

@WebMvcTest(StudyGroupController.class)
@Import(SecurityConfig.class)
public class StudyGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudyGroupRepository groupRepo;

    @MockitoBean
    private CourseRepository courseRepo;

    @MockitoBean
    private GroupMemberRepository memberRepo;

    @MockitoBean
    private UserRepository userRepo;

    @Test
    public void testShowAllGroups() throws Exception {
        when(courseRepo.findAll()).thenReturn(Collections.emptyList());
        when(groupRepo.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/groups")).andExpect(status().isNotFound());
    }
}