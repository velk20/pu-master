package org.fmi.streamline.dtos.channel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMessageToChannelDTO {
    @NotEmpty(message = "Channel id is required")
    private String channelId;
    @NotEmpty(message = "Username is required")
    private String username;
    @NotBlank(message = "Message must not be empty")
    private String message;
}
