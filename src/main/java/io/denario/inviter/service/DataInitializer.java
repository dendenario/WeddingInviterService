package io.denario.inviter.service;


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


    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(UserEntity.builder().username("guest").password(passwordEncoder.encode("2")).role("ROLE_USER").build());
            userRepository.save(UserEntity.builder().username("admin").password(passwordEncoder.encode("1")).role("ROLE_ADMIN").build());
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
                            <li>Мы очень ценим ваше внимание и заботу, но просим вас отказаться от покупки цветов. Пусть этот день будет для вас легким и беззаботным, а самым красивым букетом на нашей свадьбе станут ваши теплые объятия!</li>
                            <li>Пожалуйста, не усложняйте себе задачу выбором подарков. Самый практичный подарок для нас — в конверте, а самый ценный — ваше присутствие!</li>
                            """)
                    .build());
        }
    }
}