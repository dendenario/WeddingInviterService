package io.denario.inviter.data.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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