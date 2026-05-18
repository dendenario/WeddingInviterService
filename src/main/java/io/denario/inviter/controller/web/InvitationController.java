package io.denario.inviter.controller.web;


import io.denario.inviter.controller.rest.model.GuestDto;
import io.denario.inviter.data.repository.TextBlockEntity;
import io.denario.inviter.data.repository.TextBlockRepository;
import io.denario.inviter.service.GuestService;
import io.denario.inviter.service.WeddingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
public class InvitationController {

    private final WeddingService weddingService;
    private final GuestService guestService; // Заменено с репозитория на сервис
    private final TextBlockRepository textBlockRepository;
    private final DateTimeFormatter russianFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.of("ru"));

    public InvitationController(WeddingService weddingService, GuestService guestService, TextBlockRepository textBlockRepository) {
        this.weddingService = weddingService;
        this.guestService = guestService;
        this.textBlockRepository = textBlockRepository;
    }

    @GetMapping("/invite/{token}")
    public String showInvitationPage(@PathVariable String token, Model model) {
        GuestDto guest = guestService.getGuestByToken(token);
        if (guest == null) {
            return "redirect:/login"; // Если токен битый, отправляем на авторизацию
        }

        model.addAttribute("guestName", guest.getName());
        model.addAttribute("guestToken", guest.getToken());
        model.addAttribute("completedRsvp", guest.getCompletedRsvp());

        weddingService.getWeddingDetails()
                .ifPresent(details -> {
                    model.addAttribute("names", details.getGroomName() + " & " + details.getBrideName());
                    LocalDateTime dateTime = LocalDateTime.parse(details.getWeddingDateTime());
                    model.addAttribute("weddingDate", dateTime.format(russianFormatter));
                    model.addAttribute("weddingVenue", details.getVenueName());
                    model.addAttribute("weddingAddress", details.getAddress());
                    model.addAttribute("targetIsoDateTime", details.getWeddingDateTime());
                });

        model.addAttribute("dresscodeText", textBlockRepository.findById("DRESSCODE").map(TextBlockEntity::getContent).orElse(""));
        model.addAttribute("wishesText", textBlockRepository.findById("WISHES").map(TextBlockEntity::getContent).orElse(""));

        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
