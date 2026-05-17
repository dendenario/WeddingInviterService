package io.denario.inviter.service;

import io.denario.inviter.controller.rest.model.GuestDto;
import io.denario.inviter.data.repository.GuestEntity;
import io.denario.inviter.data.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;


    // Метод для создания нового гостя из админки (генерация токена)
    @Transactional
    public void addNewGuest(String name) {
        GuestEntity newGuest = GuestEntity.builder()
                .name(name)
                .token(UUID.randomUUID().toString().substring(0, 8)) // Короткий красивый токен из 8 символов
                .completedRsvp(false)
                .build();
        guestRepository.save(newGuest);
    }

    @Override
    @Transactional
    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public GuestDto getGuestByToken(String token) {
        return guestRepository.findByToken(token)
                .map(guestMapper::toDto)
                .orElse(null);
    }

    @Transactional
    public void saveRsvp(GuestDto dto) {
        GuestEntity guest = guestRepository.findByToken(dto.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Неверный токен приглашения"));

        guest.setAttending(dto.getAttending());
        guest.setAlcoholPreference(dto.getAlcoholPreference());
        guest.setShuttleRequired(dto.getShuttleRequired());
        guest.setCommentary(dto.getCommentary());
        guest.setCompletedRsvp(true);

        guestRepository.save(guest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestDto> getAllGuests() {
        return guestRepository.findAll().stream().map(guestMapper::toDto).toList();
    }
}
