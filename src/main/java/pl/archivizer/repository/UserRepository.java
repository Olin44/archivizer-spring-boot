package pl.archivizer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.archivizer.models.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	List<User> findAll();

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
