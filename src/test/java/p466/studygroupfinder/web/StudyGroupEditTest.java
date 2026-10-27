package p466.studygroupfinder.web;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import p466.studygroupfinder.StudyGroup;
import p466.studygroupfinder.User;
import p466.studygroupfinder.data.CourseRepository;
import p466.studygroupfinder.data.GroupMemberRepository;
import p466.studygroupfinder.data.StudyGroupRepository;
import p466.studygroupfinder.data.UserRepository;
import p466.studygroupfinder.security.SecurityConfig;

@WebMvcTest(StudyGroupController.class)
@Import(SecurityConfig.class)
class StudyGroupEditTest {

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

    private User student(Long id) {
        User user = new User("user" + id, "encoded", "First", "Last");
        user.setId(id);
        return user;
    }

    private void groupCreatedBy(Long organizerId) {
        StudyGroup group = new StudyGroup();
        group.setId(5L);
        group.setOrganizerId(organizerId);
        when(groupRepo.findById(5L)).thenReturn(Optional.of(group));
    }

    @Test
    void creatorCanOpenTheEditPage() throws Exception {
        groupCreatedBy(8L);

        mockMvc.perform(get("/groups/5/edit").with(user(student(8L))))
                .andExpect(status().isOk());
    }

    @Test
    void anotherStudentCannotOpenTheEditPage() throws Exception {
        groupCreatedBy(8L);

        mockMvc.perform(get("/groups/5/edit").with(user(student(7L))))
                .andExpect(status().isForbidden());
    }

    @Test
    void visitorIsSentToLogin() throws Exception {
        mockMvc.perform(get("/groups/5/edit"))
                .andExpect(status().is3xxRedirection());
    }
}