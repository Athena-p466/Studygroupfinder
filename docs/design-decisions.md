1. Spring security with BCrypt for registration and login
2. User class implements Userdetails
3. Browsing stays public
4. Planned for sprint 3: joining and creating groups

Design decisions (sprint3)
1. used sqlite for development, tests, and production
   1. One database engine everywhere, so there are no differences between local and deployed SQL.
      Rejected alternative: H2 locally and PostgreSQL on Render. The free PostgreSQL database
      expires after 30 days, and two engines mean schema differences that only show up after deploying.
      Consequence: on Render's free plan the SQLite file is on an ephemeral disk, so data resets at
      every restart. Seed data and demo accounts are recreated at startup.
2. A role column with two roles, and the creator as organizer
    1. A user has one role, STUDENT or ADMIN. A group's organizer is the student who created it.
3. Role rules in the filter chain, ownership rules in the controller 
      1. The filter chain decides who must log in. The controller decides whether this user created
      this group, or is an admin.
4. Demo accounts created by a startup loader 
   1. A CommandLineRunner creates the admin, student and student2 accounts and encodes their
   password with BCrypt, skipping any that exist.
