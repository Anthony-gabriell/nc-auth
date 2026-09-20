package com.nc.auth.controller;

import com.nc.auth.dto.LoginResponse;
import com.nc.auth.dto.RegisterCitizenResponse;
import com.nc.auth.dto.LoginRequest;
import com.nc.auth.dto.RegisterRequest;
import com.nc.auth.models.Citizen;
import com.nc.auth.repository.CitizenRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final CitizenRepository citizenRepository;

    public AuthController(CitizenRepository citizenRepository){
        this.citizenRepository = citizenRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        return null;
    }

    public ResponseEntity<RegisterCitizenResponse> register (@Valid @RequestBody RegisterRequest request){
        Citizen newCitizen = new Citizen();
        newCitizen.setPassword(request.password());
        newCitizen.setNome(request.nome());
        newCitizen.setClasse(request.classe());

        citizenRepository.save(newCitizen);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterCitizenResponse(newCitizen.getUsername(), newCitizen.getClasse()));

    }





}
