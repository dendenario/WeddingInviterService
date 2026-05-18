package io.denario.inviter.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeddingDetailsRepository extends JpaRepository<WeddingDetailsEntity, Long> {
    // Получаем первую (и единственную) запись настроек
    default WeddingDetailsEntity getDetails() {
        return findFirstByOrderByIdAsc().orElse(null);
    }

    java.util.Optional<WeddingDetailsEntity> findFirstByOrderByIdAsc();
}