create table if not exists Course (
    id varchar(10) not null,
    title varchar(100) not null,
    instructor varchar(100) not null,
    primary key (id)
);

create table if not exists StudyGroup (
    id identity,
    course_id varchar(10) not null,
    title varchar(50) not null,
    description varchar(500),
    location varchar(50) not null,
    meeting_time timestamp not null,
    capacity int not null
);

alter table StudyGroup add foreign key (course_id) references Course(id);