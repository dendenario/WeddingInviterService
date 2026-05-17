package io.denario.inviter.data.repository;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "text_blocks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextBlockEntity {
    @Id
    private String code; // Уникальный идентификатор: "DRESSCODE" или "WISHES"

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // Текст блока (поддерживает HTML разметку)
}