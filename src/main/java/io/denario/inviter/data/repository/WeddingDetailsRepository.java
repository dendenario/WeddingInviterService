package io.denario.inviter.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WeddingDetailsRepository extends JpaRepository<WeddingDetailsEntity, UUID> {
    // Получаем первую (и единственную) запись настроек
    default Optional<WeddingDetailsEntity> getDetails() {
        return findFirstByOrderByIdAsc();
    }

    Optional<WeddingDetailsEntity> findFirstByOrderByIdAsc();
}