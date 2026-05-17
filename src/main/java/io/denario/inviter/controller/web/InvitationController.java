package io.denario.inviter.controller.web;


import io.denario.inviter.controller.web.model.WeddingDetailsDto;
import io.denario.inviter.data.repository.TextBlockEntity;
import io.denario.inviter.data.repository.TextBlockRepository;
import io.denario.inviter.service.WeddingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


@Controller
public class InvitationController {

    private final WeddingService weddingService;
    private final TextBlockRepository textBlockRepository;
    private final DateTimeFormatter russianFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("ru"));

    public InvitationController(WeddingService weddingService, TextBlockRepository textBlockRepository) {
        this.weddingService = weddingService;
        this.textBlockRepository = textBlockRepository;
    }

    @GetMapping("/")
    public String showInvitationPage(Model model) {
        WeddingDetailsDto details = weddingService.getWeddingDetails();
        if (details != null) {
            model.addAttribute("names", details.getGroomName() + " & " + details.getBrideName());
            LocalDateTime dateTime = LocalDateTime.parse(details.getWeddingDateTime());
            model.addAttribute("weddingDate", dateTime.format(russianFormatter));
            model.addAttribute("weddingVenue", details.getVenueName());
            model.addAttribute("weddingAddress", details.getAddress());
            model.addAttribute("targetIsoDateTime", details.getWeddingDateTime());
        }

        // Подгружаем динамический текст из БД
        model.addAttribute("dresscodeText", textBlockRepository.findById("DRESSCODE").map(TextBlockEntity::getContent).orElse(""));
        model.addAttribute("wishesText", textBlockRepository.findById("WISHES").map(TextBlockEntity::getContent).orElse(""));

        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
//            <iframe th:src="@{https://yandex.ru/map-widget/v1/(mode='search',z=19,text=${weddingVenue + ' ' + weddingAddress})}"

