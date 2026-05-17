package io.denario.inviter.controller.rest;


import io.denario.inviter.controller.rest.model.GuestDto;
import io.denario.inviter.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rsvp")
@RequiredArgsConstructor
public class RsvpApiController {

    private final GuestService guestService;

    @PostMapping
    public ResponseEntity<String> submitRsvp(@Valid @RequestBody GuestDto guestDto) {
        guestService.save(guestDto);
        return ResponseEntity.ok("Ответ успешно сохранен! Ждем вас.");
    }

    @GetMapping("/list")
    public ResponseEntity<List<GuestDto>> getAllGuests() {
        return ResponseEntity.ok(guestService.getAllGuests());
    }
}
