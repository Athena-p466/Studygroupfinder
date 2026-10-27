package p466.studygroupfinder.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import p466.studygroupfinder.GroupMember;
import p466.studygroupfinder.StudyGroup;
import p466.studygroupfinder.User;
import p466.studygroupfinder.data.CourseRepository;
import p466.studygroupfinder.data.GroupMemberRepository;
import p466.studygroupfinder.data.StudyGroupRepository;
import p466.studygroupfinder.data.UserRepository;
import p466.studygroupfinder.security.SecurityConfig;

@WebMvcTest(StudyGroupController.class)
@Import(SecurityConfig.class)
class StudyGroupCreateJoinTest {

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

    private User student() {
        User user = new User("athena", "encoded", "Athena", "Student");
        user.setId(7L);
        return user;
    }

    @Test
    void visitorCannotOpenCreateForm() throws Exception {
        mockMvc.perform(get("/groups/create"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void signedInStudentCanOpenCreateForm() throws Exception {
        mockMvc.perform(get("/groups/create").with(user(student())))
                .andExpect(status().isOk())
                .andExpect(view().name("create-group"));
    }

    @Test
    void visitorCannotJoin() throws Exception {
        mockMvc.perform(post("/groups/2/join").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void signedInStudentCanJoin() throws Exception {
        StudyGroup group = new StudyGroup();
        group.setId(2L);
        group.setCapacity(4);
        when(groupRepo.findById(2L)).thenReturn(Optional.of(group));
        when(memberRepo.countByStudyGroupId(2L)).thenReturn(1L);

        mockMvc.perform(post("/groups/2/join").with(user(student())).with(csrf()))
                .andExpect(redirectedUrl("/groups"));

        verify(memberRepo).save(any(GroupMember.class));
    }
}