package io.denario.inviter.controller.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeddingDetailsDto {

    private UUID id;

    @NotBlank(message = "Имя невесты обязательно")
    private String brideName;

    @NotBlank(message = "Имя жениха обязательно")
    private String groomName;

    @NotBlank(message = "Дата торжества обязательна")
    private String weddingDateTime;

    @NotBlank(message = "Место проведения обязательно")
    private String venueName;

    @NotBlank(message = "Адрес проведения обязателен")
    private String address; // Новое поле
}
