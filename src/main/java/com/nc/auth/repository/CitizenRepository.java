package com.nc.auth.repository;

import com.nc.auth.models.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    Optional<UserDetails> findByUser(String user);
    Optional<UserDetails> findCitizenByUser(String user);
    Optional<UserDetails> findCitizenByNome(String nome);
    boolean existsByUser(String user);
    boolean existsByCityId(String cityId);
    boolean existsAllByCityId(String cityId);
}
