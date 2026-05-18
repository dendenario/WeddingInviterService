package io.denario.inviter.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, UUID> {
    Optional<GuestEntity> findByToken(String token);
}
