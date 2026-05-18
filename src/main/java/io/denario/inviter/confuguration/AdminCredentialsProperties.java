package io.denario.inviter.confuguration;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app.security.admin")
@Component
@Data
public class AdminCredentialsProperties {
    @NotBlank
    private String login;
    private String password;
}
