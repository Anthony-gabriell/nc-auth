package com.nc.auth.service;

import com.nc.auth.dto.LoginRequest;
import com.nc.auth.dto.LoginResponse;
import com.nc.auth.dto.RegisterCitizenResponse;
import com.nc.auth.dto.RegisterRequest;
import com.nc.auth.models.Citizen;
import com.nc.auth.repository.CitizenRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    private final CitizenRepository citizenRepository;

    public AuthService(CitizenRepository citizenRepository){
        this.citizenRepository = citizenRepository;
    }

    // ResponseEntity permite controlar o status HTTP e o corpo da resposta (LoginResponse)
    public LoginResponse login(LoginRequest request){
        return null;
    }

    // mtodo para registrar um novo cidadão
    // @Valid aciona as validações do RegisterRequest, @RequestBody converte o JSON recebido em objeto
    public RegisterCitizenResponse register (RegisterRequest request){
        Citizen newCitizen = new Citizen();
        newCitizen.setPassword(request.password());
        newCitizen.setNome(request.nome());
        newCitizen.setClasse(request.classe());

        citizenRepository.save(newCitizen);

        // retorna 201 com o corpo contendo apenas username e classe, sem expor a senha
        return new RegisterCitizenResponse(newCitizen.getUsername(), newCitizen.getClasse());

    }




}
