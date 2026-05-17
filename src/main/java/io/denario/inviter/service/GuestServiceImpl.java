package io.denario.inviter.service;

import io.denario.inviter.controller.rest.model.GuestDto;
import io.denario.inviter.data.repository.GuestEntity;
import io.denario.inviter.data.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;


    @Override
    public void save(GuestDto dto) {
        GuestEntity entity = guestMapper.toEntity(dto);
        guestRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestDto> getAllGuests() {
        return guestRepository.findAll().stream().map(guestMapper::toDto).toList();
    }
}
