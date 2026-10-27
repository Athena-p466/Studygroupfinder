1. Study Group Finder:
    - Study Group Finder helps students find study groups by course, join them, and create and
      edit their own groups. Built with Spring Boot 4, Java 21, Thymeleaf, Spring Data JDBC,
      Spring Security and SQLite.
    - Live site: https://studygroupfinder-yourinitials.onrender.com
    - The site runs on Render's free plan, so a visit after 15 minutes without traffic takes
      about a minute to wake the service. The free plan also keeps the SQLite file on an
      ephemeral disk: all data is erased on every restart, redeploy and wake-up, and the
      courses, groups and demo accounts below are recreated at startup.
   
You Can: Browse study groups and filter them by course (no login needed). Register, log in and log out.
      Join a group until it is full. Each group shows how many members it has.
      Create a group. You become its organizer and can edit it later.
      Admins can edit any group.

Demo Accounts: (user, password) -> format 
   - (admin, demo1234) can edit any group
   - (student, demo1234) can browse, join, create groups, and edit the groups they create
   - (student2, demo1234) can browse, join, create groups and edit their own groups, cannot edit other students' groups
   - 
Run locally (without Docker): Requires Java 21.
   - In terminal --> ./mvnw spring-boot:run 
   - Then open http://localhost:8080. The database is the file app.db, created on first start.
Stop the app and delete app.db to reset it.

Run with Docker
- in terminal -> docker build -t studygroupfinder . 
- then: docker run --rm -p 8080:8080 studygroupfinder 
- To keep data between runs: docker run --rm -p 8080:8080 -e SQLITE_PATH=/data/app.db -v appdata:/data studygroupfinder

Run tests: ./mvnw test

How the pipeline works:
- A developer pushes a branch and opens a pull request into main.
GitHub Actions (.github/workflows/ci.yml) builds the project, runs all tests
(job build-test), then builds the Docker image (job docker-build).

- A repository rule on main requires a pull request, so nothing is pushed to main directly.
After the merge, the same checks run on main.
Render (render.yaml, autoDeployTrigger: checksPass) waits for those checks, builds the
Docker image, probes /actuator/health, and only then switches the live site. 
- If a check fails, Render does not deploy.

releases: sprint1, sprint2, sprint3. release v1.0
Jira: https://yanath.atlassian.net/jira/software/projects/SGF/summary