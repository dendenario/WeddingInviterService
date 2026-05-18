package io.denario.inviter.service;

import io.denario.inviter.controller.rest.model.GuestDto;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface GuestService {
    void saveRsvp(GuestDto dto);

    @Nullable
    List<GuestDto> getAllGuests();

    void addNewGuest(String name); // Создание гостя

    void deleteGuest(UUID id);     // Удаление гостя

    GuestDto getGuestByToken(String token); // Поиск для контроллера страниц
}
