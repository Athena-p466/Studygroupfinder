CREATE TABLE IF NOT EXISTS course (
    id TEXT PRIMARY KEY,
    title TEXT NOT NULL,
    instructor TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    role TEXT NOT NULL DEFAULT 'STUDENT'
);

CREATE TABLE IF NOT EXISTS studygroup (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    course_id TEXT NOT NULL REFERENCES course(id),
    title TEXT NOT NULL,
    description TEXT,
    location TEXT NOT NULL,
    meeting_time TEXT NOT NULL,
    capacity INTEGER NOT NULL,
    organizer_id INTEGER REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS groupmember (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    study_group_id INTEGER NOT NULL REFERENCES studygroup(id),
    user_id INTEGER NOT NULL REFERENCES users(id),
    UNIQUE (user_id, study_group_id)
    );