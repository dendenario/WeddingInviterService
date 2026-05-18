package io.denario.inviter.controller.rest.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDto {
    private UUID id;
    private String name;
    private String token;
    private Boolean completedRsvp;
    private Boolean attending;
    private String alcoholPreference;
    private Boolean shuttleRequired;
    private String commentary;
}
