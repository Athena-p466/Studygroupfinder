package p466.studygroupfinder.web;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.Errors;

import jakarta.validation.Valid;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import p466.studygroupfinder.GroupMember;
import p466.studygroupfinder.StudyGroup;
import p466.studygroupfinder.Course;
import p466.studygroupfinder.User;
import p466.studygroupfinder.data.CourseRepository;
import p466.studygroupfinder.data.GroupMemberRepository;
import p466.studygroupfinder.data.StudyGroupRepository;


@Controller
@RequestMapping("/groups")
public class StudyGroupController {

    private final StudyGroupRepository groupRepo;
    private final CourseRepository courseRepo;
    private final GroupMemberRepository memberRepo;


    public StudyGroupController(StudyGroupRepository groupRepo, CourseRepository courseRepo, GroupMemberRepository memberRepo){
        this.groupRepo = groupRepo;
        this.courseRepo = courseRepo;
        this.memberRepo = memberRepo;
    }

    @ModelAttribute
    public void addCoursesToModel(Model model) {
        model.addAttribute("courses", courseRepo.findAll());

    }

    private boolean canEdit(User user, StudyGroup group){
        if (user == null) return false;
        if(user.hasRole(User.ADMIN)){
            return true;
        }
        return Objects.equals(group.getOrganizerId(), user.getId());
    }

    @GetMapping
    public String showGroups(@RequestParam(defaultValue = "") String courseId, @AuthenticationPrincipal User user, Model model) {

        List<StudyGroup> groups = courseId.isEmpty()
                ? groupRepo.findAll()
                : groupRepo.findByCourseId(courseId);

        Map<Long, Long> memberCounts = new HashMap<>();
        Set<Long> editableIds = new HashSet<>();
        for (StudyGroup group : groups) {
            memberCounts.put(group.getId(), memberRepo.countByStudyGroupId(group.getId()));
            if(canEdit(user, group)){
                editableIds.add(group.getId());
            }
        }
        model.addAttribute("selectedCourseId", courseId);
        model.addAttribute("groups", groups);
        model.addAttribute("memberCounts", memberCounts);
        model.addAttribute("editableIds", editableIds);
        return "groups";
        
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("groupForm", new StudyGroupForm());
        return "create-group";
    }

    @PostMapping("/create")
    public String createGroup(@Valid @ModelAttribute("groupForm") StudyGroupForm form,
                              Errors errors, @AuthenticationPrincipal User user) {
        if (form.getCourseId() != null && !form.getCourseId().isBlank()
                && !courseRepo.existsById(form.getCourseId())) {
            errors.rejectValue("courseId", "invalid", "Please select an existing course");
        }
        if (errors.hasErrors()) {
            return "create-group";
        }

        StudyGroup group = new StudyGroup();
        group.setCourseId(form.getCourseId());
        group.setTitle(form.getTitle());
        group.setDescription(form.getDescription());
        group.setLocation(form.getLocation());
        group.setMeetingTime(form.getMeetingTime());
        group.setCapacity(form.getCapacity());
        groupRepo.save(group);
        return "redirect:/groups";
    }

    @PostMapping("/{id}/join")
    public String joinGroup(@PathVariable Long id,
                            @AuthenticationPrincipal User user,
                            RedirectAttributes redirectAttributes) {

        Optional<StudyGroup> found = groupRepo.findById(id);
        if (found.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "This group no longer exists.");
            return "redirect:/groups";
        }
        if (memberRepo.existsByStudyGroupIdAndUserId(id, user.getId())) {
            redirectAttributes.addFlashAttribute("error", "You have already joined this group.");
            return "redirect:/groups";
        }
        if (memberRepo.countByStudyGroupId(id) >= found.get().getCapacity()) {
            redirectAttributes.addFlashAttribute("error", "This group is full.");
            return "redirect:/groups";
        }

        GroupMember member = new GroupMember();
        member.setStudyGroupId(id);
        member.setUserId(user.getId());
        try {
            memberRepo.save(member);
            redirectAttributes.addFlashAttribute("success", "You joined the group.");
        } catch (DataAccessException e) {

            redirectAttributes.addFlashAttribute("error", "You have already joined this group.");
        }
        return "redirect:/groups";
    }
    private StudyGroup findEditable(Long id, User user) {
        StudyGroup group = groupRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!canEdit(user, group)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return group;
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id,
                           @AuthenticationPrincipal User user,
                           Model model) {
        StudyGroup group = findEditable(id, user);

        StudyGroupForm form = new StudyGroupForm();
        form.setCourseId(group.getCourseId());
        form.setTitle(group.getTitle());
        form.setDescription(group.getDescription());
        form.setLocation(group.getLocation());
        form.setMeetingTime(group.getMeetingTime());
        form.setCapacity(group.getCapacity());

        model.addAttribute("groupForm", form);
        model.addAttribute("groupId", id);
        return "edit-group";
    }

    @PostMapping("/{id}/edit")
    public String editGroup(@PathVariable Long id,
                            @AuthenticationPrincipal User user,
                            @Valid @ModelAttribute("groupForm") StudyGroupForm form,
                            Errors errors,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        StudyGroup group = findEditable(id, user);

        if (form.getCourseId() != null && !form.getCourseId().isBlank()
                && !courseRepo.existsById(form.getCourseId())) {
            errors.rejectValue("courseId", "invalid", "Please select an existing course");
        }
        if (form.getCapacity() < memberRepo.countByStudyGroupId(id)) {
            errors.rejectValue("capacity", "tooSmall",
                    "Capacity cannot be lower than the current number of members");
        }
        if (errors.hasErrors()) {
            model.addAttribute("groupId", id);
            return "edit-group";
        }

        group.setCourseId(form.getCourseId());
        group.setTitle(form.getTitle());
        group.setDescription(form.getDescription());
        group.setLocation(form.getLocation());
        group.setMeetingTime(form.getMeetingTime());
        group.setCapacity(form.getCapacity());
        groupRepo.save(group);

        redirectAttributes.addFlashAttribute("success", "Group updated.");
        return "redirect:/groups";
    }
}
