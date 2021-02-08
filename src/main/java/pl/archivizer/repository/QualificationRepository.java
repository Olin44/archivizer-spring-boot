package pl.archivizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.archivizer.models.Qualification;

import java.util.List;
import java.util.Optional;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long>{
    List<Qualification> findAll();

    Optional<Qualification> findByType(String type);

}
