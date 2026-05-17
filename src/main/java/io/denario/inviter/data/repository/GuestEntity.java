package io.denario.inviter.data.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean attending;

    @Column(name = "alcohol_preference")
    private String alcoholPreference; // Крепкие, не крепкие, не пью

    @Column(name = "shuttle_required")
    private boolean shuttleRequired; // true = нужен трансфер

    private String commentary;
}
