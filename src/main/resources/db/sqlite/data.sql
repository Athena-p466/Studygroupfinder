INSERT OR IGNORE INTO course (id, title, instructor) VALUES ('CSCI-P466', 'Software Engineering', 'Dr. Bahramian');
INSERT OR IGNORE INTO course (id, title, instructor) VALUES ('CSCI-C343', 'Data Structure', 'Dr. Siddiqui');
INSERT OR IGNORE INTO course (id, title, instructor) VALUES ('MATH-M201', 'Calculus 2', 'Prof. Wang');

INSERT OR IGNORE INTO studygroup (id, course_id, title, description, location, meeting_time, capacity)
VALUES (1, 'CSCI-P466', 'Sprint 3 Work Session', 'Working on the Spring Boot assignment', 'Luddy Hall 2020', '2026-11-10T17:00:00', 4);
INSERT OR IGNORE INTO studygroup (id, course_id, title, description, location, meeting_time, capacity)
VALUES (2, 'CSCI-C343', 'Exam Review', 'Reviewing study guides and preparing for the exam', 'Wells Library Floor 2', '2026-11-12T11:00:00', 6);
INSERT OR IGNORE INTO studygroup (id, course_id, title, description, location, meeting_time, capacity)
VALUES (3, 'MATH-M201', 'Homework Help', 'Working through problem sets', 'Wells Library', '2026-11-11T17:00:00', 6);
INSERT OR IGNORE INTO studygroup (id, course_id, title, description, location, meeting_time, capacity)
VALUES (4, 'CSCI-P466', 'Quick Review', 'Short review session (capacity 2, to demo a full group)', 'Luddy Hall 2020', '2026-11-13T12:00:00', 2);