package org.fmi.stream_line.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChannelDTO {
    private String id;
    private String name;
    private String ownerId;
    private LocalDateTime createdAt;
    private List<UserDTO> memberships;
    private List<MessageDTO> messages;
}
