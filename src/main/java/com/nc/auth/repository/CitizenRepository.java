package com.nc.auth.repository;


import com.nc.auth.models.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // indica que essa interface é resp por acesso aos dados
public interface CitizenRepository extends JpaRepository<Citizen, Long> {
}
