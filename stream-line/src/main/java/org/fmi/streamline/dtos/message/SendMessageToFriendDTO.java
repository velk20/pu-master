package org.fmi.streamline.dtos.message;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendMessageToFriendDTO {
    private String senderId;
    private String friendId;
    private String message;
}
