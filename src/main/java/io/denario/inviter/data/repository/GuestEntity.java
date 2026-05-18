package io.denario.inviter.data.repository;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "guests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name; // Вы заносите имя сами: "Дядя Саша", "Маша и Артем"

    @Column(unique = true, nullable = false)
    private String token; // Уникальный UUID код для ссылки

    @Column(nullable = false)
    private boolean completedRsvp; // true = гость уже заполнил анкету

    private boolean attending;
    private String alcoholPreference;
    private boolean shuttleRequired;
    private String commentary;
}
