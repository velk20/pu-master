package org.fmi.streamline.dtos.channel;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateChannelDTO {
    @NotEmpty(message = "Channel id is required")
    private String id;
    @NotEmpty(message = "Channel name is required")
    private String name;
}
