package org.fmi.streamline.dtos.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddFriendDTO {
    @NotEmpty(message = "friend username must not be empty")
    private String friendUsername;
    @NotEmpty(message = "requester username must not be empty")
    private String requesterUsername;
}
