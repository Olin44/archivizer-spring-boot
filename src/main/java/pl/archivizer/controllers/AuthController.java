package pl.archivizer.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.archivizer.models.ERole;
import pl.archivizer.models.Role;
import pl.archivizer.models.User;
import pl.archivizer.payload.request.LoginRequest;
import pl.archivizer.payload.request.SignupRequest;
import pl.archivizer.payload.response.JwtResponse;
import pl.archivizer.payload.response.MessageResponse;
import pl.archivizer.repository.RoleRepository;
import pl.archivizer.repository.UserRepository;
import pl.archivizer.security.jwt.JwtUtils;
import pl.archivizer.security.services.UserDetailsImpl;
import pl.archivizer.services.LoginService;
import pl.archivizer.services.SignupService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	LoginService loginService;

	@Autowired
	SignupService signupService;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return loginService.login(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return signupService.registerUser(signUpRequest);
	}
}
