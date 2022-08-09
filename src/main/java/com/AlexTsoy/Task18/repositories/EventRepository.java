package com.AlexTsoy.Task18.repositories;

import com.AlexTsoy.Task18.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("SELECT e FROM Event e WHERE e.name = ?1 AND e.date = ?2")
    Optional<Event> findEventByNameAndDate(String name, LocalDateTime date);
}
