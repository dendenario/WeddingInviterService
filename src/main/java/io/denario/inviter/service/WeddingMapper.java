package io.denario.inviter.service;


import io.denario.inviter.controller.web.model.WeddingDetailsDto;
import io.denario.inviter.data.repository.WeddingDetailsEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WeddingMapper {

    public WeddingDetailsEntity toEntity(WeddingDetailsDto dto) {
        if (dto == null) return null;

        return WeddingDetailsEntity.builder()
                .id(dto.getId())
                .brideName(dto.getBrideName())
                .groomName(dto.getGroomName())
                .weddingDateTime(LocalDateTime.parse(dto.getWeddingDateTime()))
                .venueName(dto.getVenueName())
                .address(dto.getAddress()) // Маппинг нового поля
                .build();
    }

    public WeddingDetailsDto toDto(WeddingDetailsEntity entity) {
        if (entity == null) return null;

        return WeddingDetailsDto.builder()
                .id(entity.getId())
                .brideName(entity.getBrideName())
                .groomName(entity.getGroomName())
                .weddingDateTime(entity.getWeddingDateTime().toString())
                .venueName(entity.getVenueName())
                .address(entity.getAddress()) // Маппинг нового поля
                .build();
    }
}
