package io.denario.inviter.service;

import io.denario.inviter.controller.web.model.WeddingDetailsDto;

public interface WeddingService {
    WeddingDetailsDto getWeddingDetails();

    void updateWeddingDetails(WeddingDetailsDto dto);
}
