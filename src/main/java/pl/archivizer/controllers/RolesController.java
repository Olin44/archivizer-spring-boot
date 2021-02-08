package pl.archivizer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.archivizer.payload.response.RolesResponse;
import pl.archivizer.services.RoleService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class RolesController {

    private final RoleService roleService;

    @GetMapping("roles")
    public ResponseEntity<RolesResponse> getAllRoles(){
        return roleService.getAllRoles();
    }

}
