package com.nc.auth.service;

import com.nc.auth.repository.AccessLogRepository;
import com.nc.auth.repository.CitizenRepository;

public class AccessService {

    private final CitizenRepository citizenRepository;
    private final AccessLogRepository accessLogRepository;

    public AccessService(CitizenRepository citizenRepository, AccessLogRepository accessLogRepository){
        this.accessLogRepository = accessLogRepository;
        this.citizenRepository = citizenRepository;
    }

}
