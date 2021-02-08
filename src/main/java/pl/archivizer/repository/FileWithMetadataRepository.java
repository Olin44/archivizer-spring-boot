package pl.archivizer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.archivizer.models.ERole;
import pl.archivizer.models.FileWithMetadata;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface FileWithMetadataRepository extends JpaRepository<FileWithMetadata, Long> {
    boolean existsByTitle(String title);
    boolean existsById(Long id);
    Page<FileWithMetadata> findAllByUsersWithAccess_Roles_Name(@Param("name") ERole roleName, Pageable pageable);
}
