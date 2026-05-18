package io.denario.inviter.service;

import io.denario.inviter.controller.rest.model.GuestDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface GuestService {
    void saveRsvp(GuestDto dto);

    @Nullable
    List<GuestDto> getAllGuests();

    void addNewGuest(String name); // Создание гостя
    void deleteGuest(Long id);     // Удаление гостя
    GuestDto getGuestByToken(String token); // Поиск для контроллера страниц
}
