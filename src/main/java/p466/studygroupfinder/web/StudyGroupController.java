package p466.studygroupfinder.web;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.Errors;

import jakarta.validation.Valid;

import p466.studygroupfinder.StudyGroup;
import p466.studygroupfinder.Course;
import p466.studygroupfinder.data.CourseRepository;
import p466.studygroupfinder.data.StudyGroupRepository;

@Controller
@RequestMapping("/groups")
public class StudyGroupController {

    private final StudyGroupRepository groupRepo;
    private final CourseRepository courseRepo;

    public StudyGroupController(StudyGroupRepository groupRepo, CourseRepository courseRepo){
        this.groupRepo = groupRepo;
        this.courseRepo = courseRepo;
    }

    @ModelAttribute
    public void addCoursesToModel(Model model) {
        model.addAttribute("courses", courseRepo.findAll());

    }

    @GetMapping
    public String showGroups(@RequestParam(defaultValue = "") String courseId, Model model ) {
        return "groups";
    }
}
