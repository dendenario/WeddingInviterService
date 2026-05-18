package io.denario.inviter.controller.rest.model;



import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDto {
    private Long id;
    private String name;
    private String token;
    private Boolean completedRsvp;
    private Boolean attending;
    private String alcoholPreference;
    private Boolean shuttleRequired;
    private String commentary;
}
