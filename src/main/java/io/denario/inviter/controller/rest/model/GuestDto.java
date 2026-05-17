package io.denario.inviter.controller.rest.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDto {

    private Long id;

    @NotBlank(message = "Имя гостя обязательно для заполнения")
    private String name;

    @NotNull(message = "Необходимо указать статус присутствия")
    private Boolean attending;

    private String alcoholPreference;

    @NotNull(message = "Укажите, нужен ли вам трансфер")
    private Boolean shuttleRequired;

    private String commentary;
}
