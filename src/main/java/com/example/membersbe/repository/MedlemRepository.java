package com.example.membersbe.repository;

import com.example.membersbe.entity.Medlem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedlemRepository extends JpaRepository<Medlem, UUID> {

    Optional<Medlem> findByEpost(String epost);
}
