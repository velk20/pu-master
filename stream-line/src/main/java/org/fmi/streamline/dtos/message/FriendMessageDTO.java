package org.fmi.streamline.dtos.message;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendMessageDTO {
    private String userId;
    private String friendId;
}
