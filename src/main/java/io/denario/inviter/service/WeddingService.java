package io.denario.inviter.service;

import io.denario.inviter.controller.web.model.WeddingDetailsDto;

import java.util.Optional;

public interface WeddingService {
    Optional<WeddingDetailsDto> getWeddingDetails();

    void updateWeddingDetails(WeddingDetailsDto dto);
}
