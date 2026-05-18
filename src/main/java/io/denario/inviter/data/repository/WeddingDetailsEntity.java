package io.denario.inviter.data.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "wedding_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeddingDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String brideName;

    @Column(nullable = false)
    private String groomName;

    @Column(nullable = false)
    private LocalDateTime weddingDateTime;

    @Column(nullable = false)
    private String venueName;

    @Column(nullable = false)
    private String address; // Новое поле
}
