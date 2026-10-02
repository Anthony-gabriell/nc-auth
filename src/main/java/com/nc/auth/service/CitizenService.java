package com.nc.auth.service;

import com.nc.auth.models.Citizen;
import com.nc.auth.repository.AccessLogRepository;
import com.nc.auth.repository.CitizenRepository;
import org.springframework.stereotype.Service;

@Service
public class CitizenService {

    // injeção de construtor via depdencia
    private final CitizenRepository citizenRepository;
    private final AccessLogRepository accessLogRepository;

    public CitizenService(CitizenRepository citizenRepository, AccessLogRepository accessLogRepository){
        this.citizenRepository = citizenRepository;
        this.accessLogRepository = accessLogRepository;
    }

    // validação do usuario
    public ValidarUsuario(Citizen citizen){

    }

}
