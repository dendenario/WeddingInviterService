package io.denario.inviter.service;


import io.denario.inviter.confuguration.AdminCredentialsProperties;
import io.denario.inviter.data.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final WeddingDetailsRepository detailsRepository;
    private final UserRepository userRepository;
    private final TextBlockRepository textBlockRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminCredentialsProperties aCreds;


    @Override
    public void run(String... args) {
        if (userRepository.findByUsername(aCreds.getLogin()).isEmpty()) {
            userRepository.save(UserEntity.builder()
                    .username(aCreds.getLogin())
                    .password(passwordEncoder.encode(aCreds.getPassword()))
                    .role("ROLE_ADMIN").build());
        }

        if (detailsRepository.count() == 0) {
            WeddingDetailsEntity defaultDetails = WeddingDetailsEntity.builder()
                    .groomName("Денис")
                    .brideName("Мария")
                    .weddingDateTime(LocalDateTime.of(2026, 8, 29, 15, 0))
                    .venueName("Ресторан 'Кедр'")
                    .address("Сосновая улица, 3, деревня Зайцево, Одинцовский городской округ, Московская область")
                    .build();
            detailsRepository.save(defaultDetails);
        }

        // 3. Инициализация текстов
        if (textBlockRepository.count() == 0) {
            textBlockRepository.save(TextBlockEntity.builder()
                    .code("DRESSCODE")
                    .content("""
                            <p>Для нас самое главное — ваше присутствие!</p>
                            <p>Но мы будем очень признательны, если вы поддержите цветовую гамму спокойных природных оттенков!</p>
                            """)
                    .build());

            textBlockRepository.save(TextBlockEntity.builder()
                    .code("WISHES")
                    .content("""
                            <p>Мы очень ценим ваше внимание и заботу, но просим вас отказаться от покупки цветов. Пусть этот день будет для вас легким и беззаботным, а самым красивым букетом на нашей свадьбе станут ваши теплые объятия!</p>
                            <p>Ваше присутствие — лучший подарок для нас! Но если вы хотите порадовать нас подарком, мы будем благодарны, если вы выберете формат конверта. Если же вы уже подготовили для нас что-то особенное — мы примем это с огромной радостью.</p>
                            """)
                    .build());
        }
    }
}