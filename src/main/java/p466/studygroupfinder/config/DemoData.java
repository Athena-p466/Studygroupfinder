package p466.studygroupfinder.config;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import p466.studygroupfinder.StudyGroup;
import p466.studygroupfinder.User;
import p466.studygroupfinder.data.StudyGroupRepository;
import p466.studygroupfinder.data.UserRepository;


@Component
public class DemoData implements CommandLineRunner {
    private final UserRepository userRepo;
    private final StudyGroupRepository groupRepo;
    private final PasswordEncoder encoder;
    private final String demoPassword;

    public DemoData(UserRepository userRepo,
                    StudyGroupRepository groupRepo,
                    PasswordEncoder encoder,
                    @Value("${app.demo-password:demo1234}") String demoPassword) {
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
        this.encoder = encoder;
        this.demoPassword = demoPassword;
    }

    @Override
    public void run(String... args) {
        createUser("admin", "Ada", "Admin", User.ADMIN);
        User student = createUser("student", "Sam", "Student", User.STUDENT);
        createUser("student2", "Sky", "Student", User.STUDENT);

        if (groupRepo.findByOrganizerId(student.getId()).isEmpty()) {
            StudyGroup group = new StudyGroup();
            group.setCourseId("CSCI-P466");
            group.setTitle("Student Demo Group");
            group.setDescription("Created by the demo student so editing can be tried right away");
            group.setLocation("Luddy Hall 2020");
            group.setMeetingTime(LocalDateTime.now().plusDays(7).truncatedTo(ChronoUnit.MINUTES));
            group.setCapacity(5);
            group.setOrganizerId(student.getId());
            groupRepo.save(group);
        }
    }

    private User createUser(String username, String firstName, String lastName, String role) {
        User existing = userRepo.findByUsername(username);
        if (existing != null) {
            return existing;
        }
        User user = new User(username, encoder.encode(demoPassword), firstName, lastName);
        user.setRole(role);
        return userRepo.save(user);
    }
}
