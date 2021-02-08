package pl.archivizer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.archivizer.payload.response.RolesResponse;
import pl.archivizer.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public ResponseEntity<RolesResponse> getAllRoles() {
        return ResponseEntity.ok(
                RolesResponse.builder()
                .roles(roleRepository.findAll())
                .build()
        );
    }

}

