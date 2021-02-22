package pl.archivizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.archivizer.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
