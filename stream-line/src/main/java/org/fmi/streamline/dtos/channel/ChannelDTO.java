package org.fmi.streamline.dtos.channel;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.fmi.streamline.dtos.message.MessageDTO;
import org.fmi.streamline.dtos.user.UserMembershipDTO;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChannelDTO {
    private String id;
    @NotEmpty(message = "Channel name is required.")
    private String name;
    @NotEmpty(message = "Owner username is required.")
    private String ownerUsername;
    private LocalDateTime createdAt;
    private List<UserMembershipDTO> memberships;
    private List<MessageDTO> messages;
}
