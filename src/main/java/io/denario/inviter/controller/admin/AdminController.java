package io.denario.inviter.controller.admin;


import io.denario.inviter.data.repository.TextBlockEntity;
import io.denario.inviter.data.repository.TextBlockRepository;
import io.denario.inviter.service.GuestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Value("${app.base-url}")
    private String appUrl;

    private final GuestService guestService;
    private final TextBlockRepository textBlockRepository;

    public AdminController(GuestService guestService, TextBlockRepository textBlockRepository) {
        this.guestService = guestService;
        this.textBlockRepository = textBlockRepository;
    }

    @GetMapping
    public String showAdminPage(Model model) {
        model.addAttribute("guests", guestService.getAllGuests());
        model.addAttribute("totalAttending", Objects.requireNonNull(guestService.getAllGuests()).stream().filter(g -> Boolean.TRUE.equals(g.getAttending())).count());
        model.addAttribute("totalShuttle", guestService.getAllGuests().stream().filter(g -> Boolean.TRUE.equals(g.getAttending()) && Boolean.TRUE.equals(g.getShuttleRequired())).count());

        model.addAttribute("dresscodeText", textBlockRepository.findById("DRESSCODE").map(TextBlockEntity::getContent).orElse(""));
        model.addAttribute("wishesText", textBlockRepository.findById("WISHES").map(TextBlockEntity::getContent).orElse(""));

        model.addAttribute("inviteUrl", appUrl + "/invite/");

        return "admin";
    }

    // Добавление гостя в список контроля
    @PostMapping("/guest/add")
    public String addGuest(@RequestParam String name) {
        if (name != null && !name.trim().isEmpty()) {
            guestService.addNewGuest(name.trim());
        }
        return "redirect:/admin";
    }

    // Удаление гостя из списка
    @PostMapping("/guest/delete/{id}")
    public String deleteGuest(@PathVariable UUID id) {
        guestService.deleteGuest(id);
        return "redirect:/admin";
    }

    @PostMapping("/text/update")
    public String updateTexts(@RequestParam String dresscode, @RequestParam String wishes) {
        textBlockRepository.save(new TextBlockEntity("DRESSCODE", dresscode));
        textBlockRepository.save(new TextBlockEntity("WISHES", wishes));
        return "redirect:/admin";
    }
}