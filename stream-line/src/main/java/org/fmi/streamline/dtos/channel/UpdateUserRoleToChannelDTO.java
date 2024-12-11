package org.fmi.streamline.dtos.channel;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.fmi.streamline.util.validation.ValidateChannelUserRole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRoleToChannelDTO {
    @NotEmpty(message = "Channel id is required")
    private String channelId;
    @NotEmpty(message = "Username is required")
    private String username;
    @ValidateChannelUserRole(message = "User role for channel can be: OWNER, GUEST, ADMIN")
    private String role;
}
