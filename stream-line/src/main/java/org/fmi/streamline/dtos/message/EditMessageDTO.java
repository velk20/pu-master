package org.fmi.streamline.dtos.message;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditMessageDTO {
    @NotEmpty(message = "Message content is required")
    private String content;
    @NotEmpty(message = "User id is required")
    private String userId;
}
