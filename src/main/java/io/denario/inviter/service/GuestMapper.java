package io.denario.inviter.service;

import io.denario.inviter.controller.rest.model.GuestDto;
import io.denario.inviter.data.repository.GuestEntity;
import org.springframework.stereotype.Component;

@Component
public class GuestMapper {

    public GuestEntity toEntity(GuestDto dto) {
        if (dto == null) return null;
        return GuestEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .token(dto.getToken())
                .completedRsvp(dto.getCompletedRsvp() != null ? dto.getCompletedRsvp() : false)
                .attending(dto.getAttending() != null ? dto.getAttending() : false)
                .alcoholPreference(dto.getAlcoholPreference())
                .shuttleRequired(dto.getShuttleRequired() != null ? dto.getShuttleRequired() : false)
                .commentary(dto.getCommentary())
                .build();
    }

    public GuestDto toDto(GuestEntity entity) {
        if (entity == null) return null;
        return GuestDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .token(entity.getToken())
                .completedRsvp(entity.isCompletedRsvp())
                .attending(entity.isAttending())
                .alcoholPreference(entity.getAlcoholPreference())
                .shuttleRequired(entity.isShuttleRequired())
                .commentary(entity.getCommentary())
                .build();
    }
}
