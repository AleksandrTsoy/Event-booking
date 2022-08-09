package com.AlexTsoy.Task18.repositories;

import com.AlexTsoy.Task18.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    @Query("SELECT p FROM Participant p WHERE p.phoneNumber = ?1")
    Optional<Participant> findParticipantByPhoneNumber(String phoneNumber);
}
