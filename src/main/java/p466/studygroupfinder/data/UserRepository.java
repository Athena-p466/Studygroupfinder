package p466.studygroupfinder.data;

import org.springframework.data.repository.CrudRepository;
import p466.studygroupfinder.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
