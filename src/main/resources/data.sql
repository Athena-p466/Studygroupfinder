delete from StudyGroup;
delete from Course;

insert into Course (id, title, instructor) values ('CSCI-P466', 'Software Engineering',  'Dr. Bahramian');
insert into Course (id, title, instructor) values ('CSCI-C343', 'Data Structure', 'Dr. Siddiqui');
insert into Course (id, title, instructor) values ('MATH-M201', 'Calculus 2', 'Prof. Wang');

insert into StudyGroup (course_id, title, description, location, meeting_time, capacity)
values ('CSCI-P466', 'Sprint 1 Work Session', 'Working on Spring Boot assignment', 'Luddy Hall 2020', '2026-10-20 17:00:00', 4);

insert into StudyGroup (course_id, title, description, location, meeting_time, capacity)
values ('CSCI-C343', 'Exam 1 Review', 'Reviewing study guides and preparing for the first exam', 'Wells Library Floor 2', '2026-10-14 11:00:00', 6);

insert into StudyGroup (course_id, title, description, location, meeting_time, capacity)
values ('MATH-M201', 'Homework 1', 'Work on problem sets', 'Wells Library', '2026-10-20 17:00:00', 6);

