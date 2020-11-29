package pl.archivizer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.archivizer.payload.response.SimpleUserData;
import pl.archivizer.services.SimpleUserDataService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class UserController {

    SimpleUserDataService simpleUserDataService;

    public UserController(SimpleUserDataService simpleUserDataService) {
        this.simpleUserDataService = simpleUserDataService;
    }

    @GetMapping("users")
    public ResponseEntity<List<SimpleUserData>> getSimpleUserData(){
        return simpleUserDataService.getSimpleUserData();
    }
}
