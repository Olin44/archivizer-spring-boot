package pl.archivizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.archivizer.models.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
}

