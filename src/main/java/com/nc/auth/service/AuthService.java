package com.nc.auth.service;

import com.nc.auth.dto.LoginRequest;
import com.nc.auth.dto.LoginResponse;
import com.nc.auth.dto.RegisterCitizenResponse;
import com.nc.auth.dto.RegisterRequest;
import com.nc.auth.models.AccessLevel;
import com.nc.auth.models.Citizen;
import com.nc.auth.repository.CitizenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthService {

    private final CitizenRepository citizenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthService(CitizenRepository citizenRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       TokenService tokenService) {
        this.citizenRepository = citizenRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public RegisterCitizenResponse register(RegisterRequest request) {
        if (citizenRepository.existsByUser(request.user())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuário já existente, crie outro usuário.");
        }

        String cityId;
        do {
            int randomDigits = ThreadLocalRandom.current().nextInt(10000, 100000);
            cityId = "NC-2077-" + randomDigits;
        } while (citizenRepository.existsByCityId(cityId) || citizenRepository.existsAllByCityId(cityId));

        Citizen citizen = new Citizen();
        citizen.setCityId(cityId);
        citizen.setNome(request.nome());
        citizen.setUser(request.user());
        citizen.setPassword(passwordEncoder.encode(request.password()));
        citizen.setClasse(request.classe());
        citizen.setAccessLevel(AccessLevel.A);
        citizen.setEdCoin(new BigDecimal("500.00"));
        citizen.setActive(true);
        citizen.setCreatAt(new Date());

        Citizen savedCitizen = citizenRepository.save(citizen);

        return new RegisterCitizenResponse(savedCitizen.getNome(), savedCitizen.getUser(), savedCitizen.getClasse());
    }

    public LoginResponse login(LoginRequest request) {
        var authToken = new UsernamePasswordAuthenticationToken(request.user(), request.password());
        Authentication auth = authenticationManager.authenticate(authToken);
        Citizen citizen = (Citizen) auth.getPrincipal();
        String token = tokenService.gerarToken(citizen);
        return new LoginResponse(token);
    }
}
