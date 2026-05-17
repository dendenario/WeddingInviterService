package io.denario.inviter.service;

import io.denario.inviter.controller.rest.model.GuestDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface GuestService {
    void save(GuestDto dto);

    @Nullable
    List<GuestDto> getAllGuests();
}
