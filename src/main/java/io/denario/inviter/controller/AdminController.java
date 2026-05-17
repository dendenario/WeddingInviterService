package io.denario.inviter.controller;


import io.denario.inviter.data.repository.TextBlockEntity;
import io.denario.inviter.data.repository.TextBlockRepository;
import io.denario.inviter.service.GuestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final GuestService guestService;
    private final TextBlockRepository textBlockRepository;

    public AdminController(GuestService guestService, TextBlockRepository textBlockRepository) {
        this.guestService = guestService;
        this.textBlockRepository = textBlockRepository;
    }

    @GetMapping
    public String showAdminPage(Model model) {
        model.addAttribute("guests", guestService.getAllGuests());
        model.addAttribute("totalAttending", guestService.getAllGuests().stream().filter(g -> g.getAttending()).count());
        model.addAttribute("totalShuttle", guestService.getAllGuests().stream().filter(g -> g.getAttending() && g.getShuttleRequired()).count());

        // Передаем текущие тексты в форму редактирования
        model.addAttribute("dresscodeText", textBlockRepository.findById("DRESSCODE").map(t -> t.getContent()).orElse(""));
        model.addAttribute("wishesText", textBlockRepository.findById("WISHES").map(t -> t.getContent()).orElse(""));

        return "admin";
    }

    @PostMapping("/text/update")
    public String updateTexts(@RequestParam String dresscode, @RequestParam String wishes) {
        textBlockRepository.save(new TextBlockEntity("DRESSCODE", dresscode));
        textBlockRepository.save(new TextBlockEntity("WISHES", wishes));
        return "redirect:/admin"; // Перезагружаем страницу админки
    }
}
