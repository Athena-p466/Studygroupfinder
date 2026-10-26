package p466.studygroupfinder.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class StudyGroupForm {
    @NotBlank(message = "Please select a course")
    private String courseId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Meeting time is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime meetingTime;

    @Min(value = 2, message = "Capacity must be at least 2")
    @Max(value = 20, message = "Capacity must be at most 20")
    private int capacity = 4;
}
